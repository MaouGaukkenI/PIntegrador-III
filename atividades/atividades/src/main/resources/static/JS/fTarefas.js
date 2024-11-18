document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".movieRow");
    const tarSelect = document.querySelector("#tarSelect");
    const btnEdit = document.querySelector("#EditTar");
    const btnNew = document.querySelector("#NewTar");
    const btnFinish = document.querySelector("#FinishTar");
    const editForm = document.querySelector(".fncEdit");
    const addForm = document.querySelector(".fncAdd");
    const endForm = document.querySelector(".fncEnd");
    const showUserName = document.querySelectorAll(".userName");

    let editOn = false;
    let endOn = false;

    let userId;

    async function fetchUserId() {
        try {
            const response = await fetch("http://localhost:8080/getUserId", {
                method: "GET",
                credentials: "include"
            });

            if (response.ok) {
                userId = await response.text();
                console.log(userId);
            } else {
                console.log("Falha ao obter o User ID.");
            }
        } catch (error) {
            console.error("Erro:", error);
        }
    }

    fetchUserId();

    let logUser;

    async function fetchUser() {
        try {
            const response = await fetch("http://localhost:8080/getUser", {
                method: "GET",
                credentials: "include"
            });

            if (response.ok) {
                const login = document.querySelector("#userNameGet");
                const loginD = document.querySelector("#userNameGetD");
                const loginO = document.querySelector("#loginOculta");
                logUser = await response.text();
                showUserName.forEach(element => {
                    element.textContent = logUser;
                });
                login.value = `${logUser}`;
                loginD.value = `${logUser}`;
                loginO.value = `${logUser}`;
                console.log(login);
                console.log(loginD);
                console.log(loginO);
            } else {
                console.log("Falha ao obter Login do usuario!");
                logUser = "Erro!";
                showUserName.forEach(element => {
                    element.textContent = logUser;
                });

            }
        } catch (error) {
            console.error("Erro: " + error);
            logUser = "Erro!";
            showUserName.forEach(element => {
                element.textContent = logUser;
            });
        }
    }

    fetchUser();

    function formatarData(dataISO) {
        const dataObj = new Date(dataISO);

        const dia = String(dataObj.getDate()).padStart(2, '0');
        const mes = String(dataObj.getMonth() + 1).padStart(2, '0');
        const ano = dataObj.getFullYear();
        const horas = String(dataObj.getHours()).padStart(2, '0');
        const minutos = String(dataObj.getMinutes()).padStart(2, '0');

        return `${dia}/${mes}/${ano} Hr${horas}:${minutos}`;
    }

    document.querySelectorAll(".DataEdit").forEach(cell => {
        const dataISO = cell.textContent.trim();
        cell.textContent = formatarData(dataISO);

    });

    function showForm(form) {
        editForm.classList.add("hidden");
        addForm.classList.add("hidden");
        endForm.classList.add("hidden");
        editForm.classList.remove("hiddenOff");
        addForm.classList.remove("hiddenOff");
        endForm.classList.remove("hiddenOff");

        form.classList.remove("hidden");
        form.classList.add("hiddenOff");
    }

    function clearSelections() {
        tarSelect.classList.add("hidden");
        tarSelect.textContent = "Clique em uma tarefa da tabela para selecioná-la";
        editOn = false;
        endOn = false;
    }

    // Função para alternar as ações nos rows da tabela com base no contexto (editOn, endOn)
    function setupRowClickActions() {
        rows.forEach(row => {
            row.addEventListener("click", function () {
                const id = row.querySelector("td:nth-child(1)").textContent.trim();
                const titulo = row.querySelector("td:nth-child(3)").textContent.trim();
                const descricao = row.querySelector("td:nth-child(4)").textContent.trim();
                const dataFinal = row.querySelector("td:nth-child(5)").textContent.trim();
                const status = row.querySelector("td:nth-child(6)").textContent.trim();

                if (editOn) {
                    // Exibe dados no formulário de edição
                    document.querySelector("#txtIdE").value = id;
                    document.querySelector("#txtUserIdE").value = userId;
                    document.querySelector("#tituloE").value = titulo;
                    document.querySelector("#descricaoE").value = descricao;
                    document.querySelector("#datatE").value = dataFinal;
                    document.querySelector("#txtStatusE").value = status;

                    tarSelect.textContent = `Tarefa selecionada: "${titulo}"`;
                    showForm(editForm);
                } else if (endOn) {
                    document.querySelector("#txtIdE").value = id;
                    tarSelect.textContent = `Tarefa selecionada: "${titulo}" Tem certeza que deseja finalizar esta tarefa?`;
                    showForm(endForm);
                }
            });
        });
    }

    // Configura ações dos botões
    btnEdit.addEventListener("click", function () {
        clearSelections();
        editOn = true;
        tarSelect.classList.remove("hidden");
        setupRowClickActions();
    });

    btnNew.addEventListener("click", function () {
        clearSelections();
        document.querySelector("#txtUserIdA").value = userId;
        document.querySelector("#tituloA").value = "";
        document.querySelector("#descricaoA").value = "";
        document.querySelector("#datatA").value = "";
        document.querySelector("#txtStatusA").value = "ativa";
        showForm(addForm);
    });

    btnFinish.addEventListener("click", function () {
        editForm.classList.add("hidden");
        addForm.classList.add("hidden");
        editForm.classList.remove("hiddenOff");
        addForm.classList.remove("hiddenOff");

        clearSelections();
        endOn = true;
        tarSelect.classList.remove("hidden");
        setupRowClickActions();
    });

    // Envio do formulário de edição
    document.getElementById('form-editar').addEventListener('submit', async function (event) {
        event.preventDefault();
        const id = document.getElementById('txtIdE').value;
        const titulo = document.getElementById('tituloE').value;
        const descricao = document.getElementById('descricaoE').value;
        const dataFinal = document.getElementById('datatE').value;
        const status = document.getElementById('txtStatusE').value;

        try {
            const response = await fetch(`/editar/${id}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({id, titulo, descricao, datat: dataFinal, statust: status})
            });

            if (response.ok) {
                alert('Alterações salvas com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao salvar as alterações.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Erro ao processar a solicitação.');
        }
    });

    // Envio da função de finalização
    document.getElementById("btnFinalizar").addEventListener("click", async function () {
        const id = document.getElementById('txtIdE').value;
        try {
            const response = await fetch(`/endTar/${id}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            });

            if (response.ok) {
                alert('Tarefa finalizada com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao finalizar a tarefa.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Erro ao processar a solicitação.');
        }
    }, {once: true}); // Use { once: true } para evitar múltiplas associações
});