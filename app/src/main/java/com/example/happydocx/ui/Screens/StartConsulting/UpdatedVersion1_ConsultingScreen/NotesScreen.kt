package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isExpandedOne by remember { mutableStateOf(false) }
    var isExpandedTwo by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffFAFAFA))
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFAFAFA))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Consultation Notes",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff22C55E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(
                    "Save",
                    color = Color.White
                )
            }
        }
        // first card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .animateContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffFFFFFF)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffFFFFFF))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(8.dp)
                        .background(Color(0xffFFFFFF))
                ) {
                    Text("Clinical Notes", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isExpanded
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ClinicalNoteContent()
                    }
                }
            }
        }

        // elevated card 2
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .animateContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffFFFFFF)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffFFFFFF))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(8.dp)
                        .background(Color(0xffFFFFFF))
                ) {
                    Text("Prescriptions", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpandedOne = !isExpandedOne }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpandedOne
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Prescriptions()
                    }
                }
            }
        }
        // elevated card 3
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .animateContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffFFFFFF)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffFFFFFF))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(8.dp)
                        .background(Color(0xffFFFFFF))
                ) {
                    Text("Orders", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpandedTwo = !isExpandedTwo }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpandedTwo
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Orders()
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicalNoteContent(modifier: Modifier = Modifier) {
    val followUpItem = listOf<String>(
        "1 Week",
        "2 Week",
        "1 Month",
        "3 Month",
        "PRN (AS NEEDED)"
    )
    val priorityItem = listOf<String>(
        "Routine", "Urgent", "Emergency", ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {
        // one
        Text(
            text = "Chief Complaint",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Patient's primary reason for visit", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        // two
        Text(
            text = "History of Present Illness (HPI)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Detailed Description", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        // three
        Text(
            text = "Physical Examination",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Findings", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        // four
        Text(
            text = "Assessment & Diagnosis ",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        // five
        Text(
            text = "Treatment Plan",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Recommended Treatment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )

        // follow up section
        Text(
            text = "Follow-Up",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { },
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                followUpItem.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {

                        }
                    )
                }
            }

        }//....

        // Priority Section
        Text(
            text = "Priority",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { },
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                priorityItem.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {

                        }
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun Prescriptions(modifier: Modifier = Modifier) {
    val dropDownLanguageItem = listOf<String>("Hindi", "English", "Punjabi", "Marathi", "Telugu")
    val medicationList = remember { mutableStateListOf(0) }
    var nextId by remember { mutableIntStateOf(1) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
        ) {
            // language selection row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffF8FAFC)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color(0xffF8FAFC))
                        .padding(4.dp)
                ) {
                    Text(
                        "Prescription Language",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        "Select the language for printing medication instructions",
                        fontSize = 8.sp,
                        color = Color(0xff8F748B)
                    )
                }
                Spacer(Modifier.weight(1f))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF8FAFC)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Language", fontSize = 14.sp
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = false,
                            onDismissRequest = {}
                        ) {
                            dropDownLanguageItem.forEach { it ->
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }

            // render medication for each count
            medicationList.forEachIndexed { index, id ->
                MedicationData(
                    medicationNumber = index + 1,
                    showDelete = medicationList.size > 1,
                    onDelete = { medicationList.remove(id) },
                    onAddMedication = {
                        medicationList.add(nextId)
                        nextId++
                    }
                )
                if (index < medicationList.size - 1) {
                    HorizontalDivider(
                        color = Color(0xffE2E8F0)
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationData(
    modifier: Modifier = Modifier,
    medicationNumber: Int = 1,
    showDelete: Boolean = false,
    onAddMedication: () -> Unit,
    onDelete: () -> Unit = {}
) {
    val frequencyItem = listOf(
        "1 Week",
        "2 Week",
        "1 Month",
        "3 Month",
        "At Bed Time"
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xffF8FAFC))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
        ) {
            Text("Medication $medicationNumber", color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            if (showDelete) {
                IconButton(
                    onClick = { onDelete() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Black
                    )
                }
            }
        }

        Text(
            text = "Medication Name",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Dosage",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Meal Timing",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Duration",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Special Instructions (for this medication)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )

        Text(
            text = "Frequency",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { },
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                frequencyItem.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                        }
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onAddMedication() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffEFF6FF)
                )
            ) { Text("ADD MEDICATION", color = Color.Black) }
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3B82F6))
            ) {
                Text("Send To Pharmacy", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Orders(modifier: Modifier = Modifier) {
    val urgencyListItems = listOf(
        "Routine",
        "Urgent(ASAP)",
        "Stat(Immediate) "
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {

        Text(
            text = "Laboratory Tests",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Imaging Studies",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Referrals",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Expected Timeline",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
            )
        )
        Text(
            text = "Urgency",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = { },
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                urgencyListItems.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                        }
                    )
                }
            }
        }
    }
}
