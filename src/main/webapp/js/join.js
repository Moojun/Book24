
// 가입부분 체크
function signUpCheck(){

    let email = document.getElementById("email").value;
    let name = document.getElementById("jName").value;
    let password = document.getElementById("password").value;
    let passwordCheck = document.getElementById("passwordCheck").value;

    let check = true;

    // 이메일 확인
    if(email.includes('@')){
        let emailId = email.split('@')[0]
        let emailServer = email.split('@')[1]
        if(emailId === "" || emailServer === ""){
            document.getElementById("emailError").innerHTML="이메일 형식이 올바르지 않습니다.";
            check = false;
        }
        else {
            document.getElementById("emailError").innerHTML="";
        }
    }
    else {
        document.getElementById("emailError").innerHTML="이메일 형식이 올바르지 않습니다.";
        check = false;
    }


    // 이름 확인
    if(name === ""){
        document.getElementById("name_Error").innerHTML="이름이 올바르지 않습니다.";
        check = false;
    } else{
        document.getElementById("name_Error").innerHTML="";
    }


    // 비밀번호 확인
    if(password !== passwordCheck){
        document.getElementById("passwordError").innerHTML="";
        document.getElementById("passwordCheckError").innerHTML="비밀번호가 동일하지 않습니다.";
        check = false;
    } else {
        document.getElementById("passwordError").innerHTML="";
        document.getElementById("passwordCheckError").innerHTML="";
    }

    if(password === ""){
        document.getElementById("passwordError").innerHTML="비밀번호를 입력해주세요.";
        check = false;
    } else {
        //document.getElementById("passwordError").innerHTML="";
    }

    if (passwordCheck === ""){
        document.getElementById("passwordCheckError").innerHTML="비밀번호를 다시 입력해주세요.";
        check = false;
    } else {
        //document.getElementById("passwordCheckError").innerHTML="";
    }


    if(check){
        document.getElementById("emailError").innerHTML="";
        //document.getElementById("name_Error").innerHTML="";
        document.getElementById("passwordError").innerHTML="";
        document.getElementById("passwordCheckError").innerHTML="";
        return true;
    }
    else {
        return false;
    }
}