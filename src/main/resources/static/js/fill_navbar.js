async function fillNavbar(){
    let responseAuthorizedUser = await fetch('http://localhost:8080/user/api/get_autorized_user')
    let content = await responseAuthorizedUser.json()
    let list = document.querySelector('.jsFillNavBar')
    let listRolesCurrUser = ""
    let key
    // Делаем перечень ролей в одну строку без префикса "ROLE_"
    for (key in content["roles"]){
        listRolesCurrUser += " " + (content["roles"][key].name).replace("ROLE_", "")
    }
    list.innerHTML += `
            <span class="nav_text_bold">${content.login}</span>
            with roles:
            <span>${listRolesCurrUser}</span>
        `


}
fillNavbar()