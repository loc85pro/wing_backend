let lable = document.getElementsByTagName("label");
let input = document.getElementsByTagName("input");
//----------------------------
function validation_Form() {
    for (let val of lable) {
        val.style.color = "black";
        val.style.fontWeight = "500";
    }
    check_Empty_Input();
}
function warn_Invalid(val) {
    val.style.color = "red";
    val.style.fontWeight = "1000";
}
function check_Empty_Input(){
    for (let i = 0; i < 4; i++)
        if (!input[i].value)
            warn_Invalid(lable[i]);
    if ((!input[4].checked)&&(!input[5].checked))
        warn_Invalid(lable[4]);
    for (let i = 6; i < 8; i++)
        if (!input[i].value)
            warn_Invalid(lable[i-1]);
}
function validation_code (val) {
    if (val.length!=8)
        return false;
    let n = Number(val);
    if (!n)
        return false;
    if (n<17000000||n>22000000)
        return false;
    return true;
}
function validation_name (val) {
    let count = 0;
    val=' '+val;
    for (let i=0;i<val.length-1;i++)
        if (val[i]==' ' && val[i+1]!=' ')
            count++;
    if (count<2)
        return false;
    return true;
}
function validation_phone (val) {
    if (val[0]!=0)
        return 0;
    if (val.length!=10)
        return 0;
    return 1;
}
function validation_email(emailToTest) {
    var atSymbol = emailToTest.indexOf("@");
    if(atSymbol < 1) return false;
    var dot = emailToTest.indexOf(".");
    if(dot <= atSymbol + 2) return false;
    if (dot === emailToTest.length - 1) return false;
    return true;
}
//-------------
let even_Row = 0;
function addToRegisted() {
    let new_Elelemt_Result = [];
    for (let i=0;i<4;i++)
    {
        new_Elelemt_Result[i] = document.createElement("p");
    }
    new_Elelemt_Result[0].appendChild(document.createTextNode(input[0].value));
    new_Elelemt_Result[1].appendChild(document.createTextNode(input[1].value));
    if (input[4].checked)
        new_Elelemt_Result[2].appendChild(document.createTextNode("Nam"));
    else
        new_Elelemt_Result[2].appendChild(document.createTextNode("Nữ"));
    new_Elelemt_Result[3].appendChild(document.createTextNode(input[6].value));
    let target_Table = document.getElementById("result");
    for (let i=0;i<4;i++)
        target_Table.appendChild(new_Elelemt_Result[i]);
    for (let i=0;i<4;i++)
    {
        new_Elelemt_Result[i].style.height = "100%";
        if (even_Row)
            new_Elelemt_Result[i].style.backgroundColor = "#FFBE7E";
        else
            new_Elelemt_Result[i].style.backgroundColor = "white";
    }
    if (even_Row)
        even_Row--;
    else
        even_Row++;
}
function remove_All() {
    for (let i in input)
        input[i].value = "";
    input[4].checked = input[5].checked = 0;
    all_leftShift();
}
//-------------
function submit_Form() {
    let dk = 1;
    validation_Form();
    if (!validation_code(input[0].value))
        {
            warn_Invalid(lable[0]);
            dk = 0;
        }
    if (!validation_name(input[1].value))
        {
            warn_Invalid(lable[1]);
            dk = 0;
        }
    if (!validation_name(input[2].value)) {
        warn_Invalid(lable[2]);
        dk = 0;
    }
    if (!validation_phone(input[3].value)) {
        warn_Invalid(lable[3]);
        dk = 0;
    }
    if (!validation_email(input[7].value)) {
        warn_Invalid(lable[6]);
        dk = 0;
    }
    if (dk)
    {
        addToRegisted();
        remove_All();
    }
}
//-----------------DRAG DROP SUBJECT ---------------------
let subject = [];
for (let i=1;i<11;i++)
    subject[i-1] = document.getElementById(String(i));
let avai_box = document.getElementById("available-list");
let registed_box = document.getElementById("registed-list");
let picking = "";
function lable_Grapping(val)
{
    picking = val;
}
function allowDrop(event) {
    event.preventDefault();
}
function drop(ele) {
    let packet = document.getElementById(picking)
    if (ele.id=="registed-list") 
        packet.style.backgroundColor="#FFBF55";
    else
        packet.style.backgroundColor="white";
    ele.insertBefore(packet,ele.id=="registed-list"?document.getElementById("registed-clone"):document.getElementById("avail-clone"));
}
/*------------------ switch subject by button ----------*/
let right_Shift_Button = document.getElementById("right-shift");
let left_Shift_Button = document.getElementById("left-shift");
let all_Right_Shift_Button = document.getElementById("all-right-shift");
let all_Left_Shift_Button = document.getElementById("all-left-shift");


let switch_List = [];
for (let i=0;i<10;i++)
    subject[i].addEventListener('click',
        function() {
            switch_List.push(subject[i]);
            subject[i].style.backgroundColor = "#FF8207";
            subject[i].style.color = "white";
        });

right_Shift_Button.addEventListener('click', rightShift);
left_Shift_Button.addEventListener('click', leftShift);
all_Right_Shift_Button.addEventListener('click', all_rightShift);
all_Left_Shift_Button.addEventListener('click', all_leftShift);

function rightShift() {
    for (let i in switch_List)
    {
        switch_List[i].style.color = "black";
        switch_List[i].style.backgroundColor = "#FFBF55";
        registed_box.appendChild(switch_List[i]);
    }
    switch_List = [];
}
function leftShift() {
    for (let i in switch_List)
    {
        switch_List[i].style.color = "black";
        switch_List[i].style.backgroundColor = "white";
        avai_box.appendChild(switch_List[i]);
    }
    switch_List = [];
}
function all_rightShift() {
    for (let i =0;i<10;i++)
        switch_List.push(subject[i]);
    rightShift();
}
function all_leftShift() {
    for (let i =0;i<10;i++)
        switch_List.push(subject[i]);
    leftShift();
}
//-----------SIDE-----------
function dropdown_Click(val) {
    let content = document.getElementById(val);
    if (content.style.display=="")
        content.style.display = "none";
    else
        if (content.style.display=="none") 
            content.style.display = "block";
        else
            content.style.display = "none";
}
function drag_News(ele) {
    let graged = ele.parentElement.parentElement;
    graged.setAttribute("draggable","true");
}
function undrag_News(ele) {
    let graged = ele.parentElement.parentElement;
    graged.setAttribute("draggable","false");
}
let dragging_News_Picking = null;
function dragging_News(ele) {
    dragging_News_Picking = ele;
}
function drop_News(ele) {
    let tit_1 = dragging_News_Picking.firstElementChild.lastElementChild.textContent;
    let tit_2 = ele.firstElementChild.lastElementChild.textContent;
    let cont_1 = dragging_News_Picking.lastElementChild.textContent;
    let cont_2 = ele.lastElementChild.textContent;
    dragging_News_Picking.firstElementChild.lastElementChild.innerHTML = tit_2;
    ele.firstElementChild.lastElementChild.innerHTML = tit_1;
    dragging_News_Picking.lastElementChild.innerHTML = cont_2;
    ele.lastElementChild.innerHTML = cont_1;
}
//---
let footer_Menu = document.getElementsByClassName("footer-menu");
function active_Footer (ele) {
    for (let i=0;i<5;i++)
        {
            footer_Menu[i].style.color = "#FF8207";
            footer_Menu[i].style.backgroundColor = "inherit";
        }
    ele.style.color="white";
    ele.style.backgroundColor = "#FF8207";
}

let nav_Menu = document.getElementsByClassName("nav-menu");
function active_Nav (ele) {
    for (let i=0;i<5;i++)
        {
            nav_Menu[i].style.color = "#1869D8";
            nav_Menu[i].style.textShadow = "0 0 0 0"
        }
    ele.style.color="#FFBE7E"
    ele.style.textShadow = "0 0 5px grey";
}