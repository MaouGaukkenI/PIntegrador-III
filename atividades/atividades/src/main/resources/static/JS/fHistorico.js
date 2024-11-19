document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".movieRow");
    const tarSelect = document.querySelector("#tarSelect");
    const btnRec = document.querySelector("#recTar");
    const btnDell = document.querySelector("#dellTar");
    const dellForm = document.querySelector(".fncDell");
    const recForm = document.querySelector(".fncRec");
    const showUserName = document.querySelectorAll(".userName");

    let dell = false;
    let rec = false;

    let userId;

    async function fetchUserId() {
        try {
            const response = await fetch("http://localhost:8080/getUserId", {
                method: "GET",
                credentials: "include"
            });

            if (response.ok) {
                userId = await response.text();
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
            } else {
                console.log("Falha ao obter Login do usuario!");
                logUser = "Erro!";
                showUserName.forEach(element => {
                    element.textContent = logUser;
                });

            }
        } catch (error) {
            console.error("Erro: " + error);
        }
    }

    fetchUser();

    var idG = -1;

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

    function showform(form) {
        dellForm.classList.add("hidden");
        recForm.classList.add("hidden");
        dellForm.classList.remove("hiddenOff");
        recForm.classList.remove("hiddenOff");

        form.classList.remove("hidden");
        form.classList.add("hiddenOff");
    }

    function hiddenform() {
        dellForm.classList.add("hidden");
        recForm.classList.add("hidden");
        dellForm.classList.remove("hiddenOff");
        recForm.classList.remove("hiddenOff");
    }

    function clearSelections() {
        tarSelect.classList.add("hidden");
        tarSelect.textContent = "Clique em uma tarefa da tabela para selecioná-la";
        dell = false;
        rec = false;
    }

    function setupRowClickActions() {
        tarSelect.classList.remove("hidden");
        rows.forEach(row => {
            row.addEventListener("click", function () {
                const id = row.querySelector("td:nth-child(1)").textContent.trim();
                const titulo = row.querySelector("td:nth-child(3)").textContent.trim();
                idG = id;
                if (dell) {
                    tarSelect.textContent = `Tarefa selecionada: "${titulo}" Tem certeza que deseja deleta-la?`;
                    showform(dellForm);
                } else if (rec) {
                    tarSelect.textContent = `Tarefa selecionada: "${titulo}" Quer mesmo recuperar a tarefa?`;
                    showform(recForm);
                }
            });
        });
    }

    btnDell.addEventListener("click", function () {
        clearSelections();
        dell = true;
        hiddenform();
        setupRowClickActions();
    });

    btnRec.addEventListener("click", function () {
        clearSelections();
        rec = true;
        hiddenform();
        setupRowClickActions();
    });

    // Envio da função de recuperar a tarefa
    document.getElementById("btnRec").addEventListener("click", async function () {
        const id = idG;
        try {
            const response = await fetch(`/recTar/${id}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            });

            if (response.ok) {
                alert('Tarefa Recuperada com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao Recuperar a tarefa.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Erro ao processar a solicitação.');
        }
    }, {once: true});

    // Envio da função de exclusão da tarefa
    document.getElementById("btnDell").addEventListener("click", async function () {
        const id = idG;
        try {
            const response = await fetch(`/dell/${id}`, {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json'}
            });

            if (response.ok) {
                alert('Tarefa Excluida com sucesso!');
                window.location.reload();
            } else {
                alert('Erro ao Excluir a tarefa.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Erro ao processar a solicitação.');
        }
    }, {once: true});
});