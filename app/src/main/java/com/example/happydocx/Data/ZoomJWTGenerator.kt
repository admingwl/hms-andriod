package com.example.happydocx.Data

import android.util.Base64
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object ZoomJWTGenerator {

    // for Demo only. generate token from backend in production time.
    private const val SDK_KEY = "4lKmrX4DRbGKVhjHluNA"
    private const val SDK_SECRET = "f3dY2c9AX0tJbEY6rxzQ4EMwXk8i38Mx"

    fun generateTokenZoom(
        sessionName: String,
        roleType: Int
    ): String {
         // JWT has 3 parts.
        //1. JWT Header --> contain the algo in which you safe the token. and type of the token.
        // lets first create the header
        val header = JSONObject().apply {
            put("alg", "HS256") // you tell which algo to use to seal the token
            put("typ", "JWT") // type of the token
        }
        // 2. JWT payload.--> The payload contains statements about the entity (typically, the user)
        val payLoad = JSONObject().apply {
            put("app_key", SDK_KEY)
            put("tpc", sessionName)
            put("role_type", roleType)
            put("version",1)
            put("iat", System.currentTimeMillis() / 1000) // (issued At) at what specific time this token is generated
            put("exp", (System.currentTimeMillis() / 1000) + 3600)
        }

        // 3. JWT signature ->
            // a. The signature is used to verify that the sender of the JWT is who it says it is and to ensure
        // that the message wasn’t changed along the way.

           // b. To create the signature, the Base64-encoded header and payload are taken,
        // along with a secret, and signed with the algorithm specified in the header.

        val headerEncoded = base64UrlEncoded(input = header.toString())
        val payloadEncoded = base64UrlEncoded(payLoad.toString())
        val signature = sign(data = "$headerEncoded.$payloadEncoded", secret = SDK_SECRET)

        return "$headerEncoded.$payloadEncoded.$signature" // final jwt token
    }

    // create function for the base64UrlEncoded
    private fun base64UrlEncoded(
        input:String
    ):String{
     return Base64.encodeToString(
         input.toByteArray(StandardCharsets.UTF_8),
         Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
     )
    }

    // sign function for signature
    private fun sign(
        data:String,
        secret:String
    ):String{
        //Hash-based Message Authentication Code) ek technique hai {HMAC}
        //SHA256 ek hashing algorithm hai jo kisi bhi bade data ko ek fix length (256-bit) ke kachre (gibberish) mein badal deta hai.
        /**
         * Agar message ka ek . (dot) bhi badla, toh ye final signature poori tarah badal jayega.
         * Isse server ko pata chal jata hai ki kisi ne token ke saath chhed-chaad ki hai.
         */
        val sha256HMAC = Mac.getInstance("HmacSHA256")
        //Yahan tu apni Secret String ko ek aisi form mein badal raha hai jise Mac machine samajh sake.
        val secretKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8),"HmacSHA256")
        //Machine taiyar hai, chabi (key) lag chuki hai. Ab bas data daalna baki hai.
        sha256HMAC.init(secretKey)
        /**
         * Jo bytes doFinal ne diye, wo insaan nahi padh sakta (unreadable characters).
         * Isliye humne wahi Base64URL use kiya taaki wo ek saaf-suthri string ban jaye jise hum internet par bhej sakein.
         */
        return Base64.encodeToString(
            //doFinal ka matlab hai: "Ye lo data, iska hash nikalo, aur is par meri key ki mohar laga kar mujhe bytes wapas do."
            sha256HMAC.doFinal(data.toByteArray(StandardCharsets.UTF_8)),
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
        )
    }
}


// Base 64 Url explaination...
/**
 * 1. Base64 Kya Hai? (The Logic)
 * Computer sirf 0 aur 1 samajhta hai (Binary). Lekin jab hamein data (jaise image, ya koi complicated string) ek
 * jagah se doosri jagah bhejni hoti hai, toh binary data aksar "break" ho jata hai kyunki kuch systems special
 * characters ko alag tarah treat karte hain.
 *
 * Base64 ka kaam hai kisi bhi binary data ko 64 safe characters (A-Z, a-z, 0-9, +, /)
 * mein badal dena. Isse data "Text-only" ban jata hai aur bina kharab hue travel kar sakta hai.
 *
 * 2. Base64 "URL" Encode Kyun? (The Problem)
 * Yahan aati hai asli engineering. Normal Base64 mein do characters hote hain: + (plus) aur / (slash).
 * Ab imagine kar, tu ye encoded string ek URL (website link) mein bhej raha hai:
 * mysite.com/token = SGk+VGhlcmUv
 * Masla: URL mein + ka matlab hota hai "Space", aur / ka matlab hota hai "Folder path".
 * Result: Agar tera token URL mein gaya, toh browser use galat samajh lega aur tera data corrupt ho jayega.
 *
 * 3. Solution: Base64URL
 * Principle coding ka rule hai: System ko foolproof banao. Base64URL encoding normal Base64 hi hai, bas
 * do chhote lekin krantikari (revolutionary) badlav ke saath:
 * + ki jagah - (minus) use hota hai.
 * / ki jagah _ (underscore) use hota hai.
 *
 * Padding (=) hata di jati hai: Normal Base64 mein end mein = lagta hai complete karne ke liye,
 * par URL mein = ka matlab "query parameter" hota hai, isliye isse delete kar dete hain.
 */