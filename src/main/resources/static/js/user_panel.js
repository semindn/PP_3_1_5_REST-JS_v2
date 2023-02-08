async function fillTableCurrentUser(){
    //получаем ID текущего пользователя из html
    // "tableSingleUser" - id html блока, из которого забираем значение аттрибута
    // "data-place" - аттрибут html блока в который помещено значение ID текущего пользователя
    // let idCurrenUser = document.getElementById("tableCurrentUser").getAttribute("data-place")
    //
    // let response = await fetch('http://localhost:8080/api/user/'+idCurrenUser)
    // let content = await response.json()

    let responseAuthorizedUser = await fetch('http://localhost:8080/user/api/get_autorized_user')
    let content = await responseAuthorizedUser.json()

    let list = document.querySelector('.jsTableCurrentUser')
    let listRolesCurrUser = ""
    let key
    // Делаем перечень ролей в одну строку без префикса "ROLE_"
    for (key in content["roles"]){
        listRolesCurrUser += " " + (content["roles"][key].name).replace("ROLE_", "")
    }
    list.innerHTML += `
            <tr>
                <td>${content.id}</td>
                <td>${content.firstName}</td>
                <td>${content.lastName}</td>
                <td>${content.age}</td>
                <td>${content.login}</td>
                <td>${listRolesCurrUser}</td>   
            </tr>
        `
}
fillTableCurrentUser()
