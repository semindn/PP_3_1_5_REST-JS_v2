const tBody = document.querySelector("tbody")

const newUser = document.querySelectorAll(".create-user")
const usersTable = document.querySelectorAll(".users-table")

const tableContainer = document.getElementById("table-container")

//--------форма создания нового пользователя
const createUserForm = document.querySelector(".add-user-post-form")

//--------------URLы для запросов в fetch
const urlGetUsers = "http://localhost:8080/admin/api/list_users"
const urlGetAllRoles = "http://localhost:8080/admin/api/get_all_roles"
const urlCreateUser = "http://localhost:8080/admin/api/save_new_user"
const urlGetUserById = "http://localhost:8080/admin/api/user"
const urlUpdateUser = "http://localhost:8080/admin/api/update_exists_user"
const urlDeleteUser = "http://localhost:8080/admin/api/delete_user"

//-------------------------- Переменные для элементов формы добавления нового пользователя
const userFirstName = document.getElementById("firstName")
const userLastName = document.getElementById("lastName")
const userAge = document.getElementById("age")
const userLogin = document.getElementById("login")
const userPass = document.getElementById("pass")
const userRoleSelect = document.getElementById("allRoles")
//--------------------------------

let modal = "";
let modalForm = "";
let submitBtn = "";

let output = ``
const renderTable = (users) => {
    users.forEach((user) => {
        output += `
           <tr id=${user.id}>
  <td>${user.id}</td>
  <td>${user.firstName}</td>
  <td>${user.lastName}</td>
  <td>${user.age}</td>
  <td>${user.login}</td>
  <td>${user.rolesToString}</td>
  <td data-id="${user.id}">
    <!--Кнопка модалки редактирования-->
    <button
      id="edit-button"
      class="btn btn-info button-custom-edit action-button edit-button "
      data-toggle="modal"
      data-target="#editModal"
    >
      Edit
    </button>
    <!--Модалка редактирования. Начало-->
    <div
      id="editModal"
      data-id="${user.id}"
      class="modal fade"
      tabindex="-1"
      aria-labelledby="editModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editModalLabel">Edit user</h5>
            <button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form data-id="editForm" class="edit-user-put-form">
              <div>
                <label for="id" class="form-label">Id</label>
                <input
                  type="text"
                  id="id"
                  class="form-control"
                  placeholder="Id"
                  required
                  disabled
                />
              </div>
              <div>
                <label for="firstName" class="form-label">First name</label>
                <input
                  type="text"
                  id="firstName"
                  class="form-control"
                  placeholder="First name"
                  required
                />
              </div>

              <div>
                <label for="lastName" class="form-label">Last name</label>
                <input
                  type="text"
                  id="lastName"
                  class="form-control"
                  placeholder="Last name"
                  required
                />
              </div>

              <div>
                <label for="age" class="form-label">Age</label>
                <input
                  type="text"
                  id="age"
                  class="form-control"
                  placeholder="Age"
                  required
                />
              </div>

              <div>
                <label for="login" class="form-label">Login</label>
                <input
                  type="text"
                  id="login"
                  class="form-control"
                  placeholder="Login"
                  required
                />
              </div>

              <div>
                <label for="pass" class="form-label">Password</label>
                <input
                  type="password"
                  id="pass"
                  class="form-control"
                  placeholder="Password"
                />
              </div>

              <div class="mb-3">
                <label for="allRoles" class="form-label">Role</label>
                <select
                  class="form-select"
                  id="allRoles"
                  name="allRolesName"
                  multiple
                ></select>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button"
                    class="btn btn-secondary action-button"
                    data-dismiss="modal">Close
            </button>
            <button
              type="submit"
              form="editForm"
              class="btn btn-primary submit"
              data-dismiss="modal"
            >
              Edit
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--Модалка редактирования. Конец-->
  </td>
    
  <td data-id="${user.id}">
    <!--Кнопка модалки удаления-->
    <button
      id="delete-button"
      class="btn btn-danger button-custom-delete action-button delete-button"
      data-toggle="modal"
      data-target="#deleteModal">
      Delete
    </button>
    <!--Модалка удаления. Начало-->
    <div id="deleteModal" class="modal fade" data-id="${user.id}" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="deleteModalLabel">Delete user</h5>
            <button type="button" class="btn btn-close" data-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form class="delete-user-form">
              <div>
                <label for="id" class="form-label">Id</label>
                <input
                  type="text"
                  id="id"
                  class="form-control"
                  placeholder="Id"
                  
                  disabled
                />
              </div>
              <div>
                <label for="firstName" class="form-label">First name</label>
                <input
                  type="text"
                  id="firstName"
                  class="form-control"
                  placeholder="First name"
                  disabled
                />
              </div>

              <div>
                <label for="lastName" class="form-label">Last name</label>
                <input
                  type="text"
                  id="lastName"
                  class="form-control"
                  placeholder="Last name"
                  required
                  disabled
                />
              </div>

              <div>
                <label for="age" class="form-label">Age</label>
                <input
                  type="text"
                  id="age"
                  class="form-control"
                  placeholder="Age"
                  required
                  disabled
                />
              </div>

              <div>
                <label for="login" class="form-label">Login</label>
                <input
                  type="text"
                  id="login"
                  class="form-control"
                  placeholder="Login"
                  required
                  disabled
                />
              </div>

              <div>
                <label for="pass" class="form-label">Password</label>
                <input
                  type="password"
                  id="pass"
                  class="form-control"
                  placeholder="Password"
                  disabled
                />
              </div>
              

              <div class="mb-3">
                <label for="allRoles" class="form-label">Role</label>
                <select
                disabled
                  class="form-select"
                  id="allRoles"
                  name="allRolesName"
                  multiple
                ></select>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button"
                    class="btn btn-secondary action-button"
                    data-dismiss="modal">Close
            </button>
            <button
              type="submit"
              class="btn btn-primary submit"
              data-dismiss="modal"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--Модалка удаления. Конец-->
  </td>
</tr>

`})

    tBody.innerHTML = output
}


//Get all users
fetch(urlGetUsers)
    .then((response) => response.json())
    .then((data) => renderTable(data))

// Get all roles
fetch(urlGetAllRoles)
    .then((response) => response.json())
    .then((data) => setSelectOpt(data))

const setSelectOpt = (roles) => {
    userRoleSelect.size = roles.length
    const options = roles

    for (let i = 0; i < options.length; i++) {
        let roleName = options[i].name
        let el = document.createElement("option")
        el.textContent = roleName
        el.value = roleName
        userRoleSelect.appendChild(el)
    }
}

createUserForm.addEventListener("submit", (e) => {
    e.preventDefault()
    output="";
    // '...' переводит в массив значение HTML элемента
    let selectedValues = [...userRoleSelect.options]
        .filter((x) => x.selected)
        .map((x) => x.value)

    fetch(urlCreateUser, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            firstName: userFirstName.value,
            lastName: userLastName.value,
            age: userAge.value,
            login: userLogin.value,
            passw: userPass.value,
            roles: selectedValues,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            renderTable(data)})

    createUserForm.reset()

    newUser.forEach((u) => u.classList.remove("active"))
    usersTable.forEach((u) => u.classList.add("active"))

})

tableContainer.addEventListener("click", (e) => {
    e.preventDefault()

    let editButtonIsPressed = e.target.id == "edit-button"
    let deleteButtonIsPressed = e.target.id == "delete-button"

    let id = e.target.parentElement.dataset.id

    if (deleteButtonIsPressed) {
        getModal(id, "delete")
    }
    if (editButtonIsPressed) {
        getModal(id, "edit")
    }

    function getModal(id, mode) {
        if (mode === "edit") {
            modal = document.getElementById("editModal")
            modalForm = modal.querySelector(".edit-user-put-form")
            submitBtn = modal.querySelector(".submit")
            modal.remove();
        }
        if (mode === "delete") {
            modal = document.getElementById("deleteModal")
            modalForm = modal.querySelector(".delete-user-form")
            submitBtn = modal.querySelector(".submit")
            modal.remove();

        }
        const btnClose = modal.querySelectorAll(".btn-close")
        btnClose.forEach((e) => {
            e.addEventListener("click", (e) => {
                e.preventDefault()
                modal.remove()
            })
        })
        output = ""
        let pass="";

        fetch(`${urlGetUserById}/${id}`)
            .then((response) => response.json())
            .then((user) => {

                modalForm.id.value = user.id
                modalForm.firstName.value = user.firstName
                modalForm.lastName.value = user.lastName
                modalForm.age.value = user.age
                modalForm.login.value = user.login
                // modalForm.pass.value = user.pass;

                const userRoleSelect = modalForm.allRoles
                const userRoles = user.roles

                fetch(urlGetAllRoles)
                    .then((response) => response.json())
                    .then((data) => {
                        setSelectOpt(data)
                    })
                const setSelectOpt = (roles) => {
                    userRoleSelect.size = roles.length
                    for (let i = 0; i < roles.length; i++) {
                        let roleName = roles[i].name
                        let roleText = roles[i].name.replace(
                            roles[i].name.substring(0, 5),
                            ""
                        )
                        let el = document.createElement("option")
                        el.textContent = roleText
                        el.value = roleName
                        userRoles.forEach((e) => {
                            if (e.name === roleName) {
                                el.selected = true
                            }
                        })
                        userRoleSelect.appendChild(el)
                    }
                }


                if (mode === "edit") {

                    submitBtn.addEventListener("click", (e) => {
                        e.preventDefault()
                        let selectedValues = [...userRoleSelect.options]
                            .filter((x) => x.selected)
                            .map((x) => x.value)
                        fetch(urlUpdateUser, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json",
                            },
                            body: JSON.stringify({
                                id: modalForm.id.value,
                                firstName: modalForm.firstName.value,
                                lastName: modalForm.lastName.value,
                                age: modalForm.age.value,
                                login: modalForm.login.value,
                                pass: modalForm.pass.value,
                                roles: selectedValues,
                            }),
                        })
                            .then((response) => response.json())
                            .then((data) => renderTable(data))
                    })
                }
                if (mode === "delete") {
                    let selectedValues = [...userRoleSelect.options]
                        .filter((x) => x.selected)
                        .map((x) => x.value)
                    submitBtn.addEventListener("click", (e) => {
                        e.preventDefault()
                        fetch(`${urlDeleteUser}/${id}`, {
                            method: "DELETE",
                        })
                            .then((response) => response.json())
                            .then((data) => renderTable(data))
                    })
                }
            })
    }
})

