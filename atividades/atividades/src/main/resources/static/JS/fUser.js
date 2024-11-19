document.addEventListener("DOMContentLoaded", function () {
    const btnOpen = document.querySelector("#caseUserName");
    const menuUser = document.querySelector("#UserSettings");
    const menuClose = document.querySelector("#closeMenuUser");

    const btnEditUser = document.querySelector("#editarOpen");
    const telaEdit = document.querySelector("#editUserArea");
    const editClose = document.querySelector("#closeEditUser");

    const campoSenha = document.querySelector("#credenciais");
    const selecionarFunc = document.querySelector("#SelecionarFuncao");
    const editUser = document.querySelector("#EditarUsuario");
    const editSenha = document.querySelector("#EditarSenha");
    const telaErro = document.querySelector("#ErroUS");
    const userCampo = document.querySelector("#editarUsuario");
    const telaFim = document.querySelector("#finish");
    const btnUser = document.querySelector("#EUser");
    const btnSenha = document.querySelector("#ESenha");
    const btnTenNov = document.querySelector("#TN");
    const btnCan = document.querySelector("#Ca");
    const btnFinish = document.querySelector("#btnFim");

    const btnDeslog = document.querySelector("#deslogar");
    const btnDellUser = document.querySelector("#dellUser");

    const telaErroDell = document.querySelector("#ErroUSDell");
    const campoSenhaDell = document.querySelector("#credenciaisDell");
    const btnTenNovDell = document.querySelector("#TND");
    const btnCanDell = document.querySelector("#CaD");

    function closeAll() {
        telaEdit.classList.add("hidden");
        campoSenha.classList.add("hidden");
        campoSenhaDell.classList.add("hidden");
        selecionarFunc.classList.add("hidden");
        editUser.classList.add("hidden");
        editSenha.classList.add("hidden");
        telaErro.classList.add("hidden");
        telaErroDell.classList.add("hidden");
        userCampo.classList.add("hidden");
        telaFim.classList.add("hidden");
    }

    btnDellUser.addEventListener("click", function () {
        campoSenhaDell.classList.remove("hidden");
        userCampo.classList.remove("hidden");
        telaEdit.classList.remove("hidden");

    });

    document.getElementById("loginFormDell").addEventListener("submit", async function (e) {
        e.preventDefault();

        const login = document.querySelector("#userNameGetD").value;
        const senha = document.getElementById("txtSenhaD").value;

        if (!senha) {
            alert("Por favor, insira uma senha.");
            return;
        }

        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({login, senha})
        });

        if (response.ok) {
            campoSenhaDell.classList.add("hidden");
            senha.value = ``;

            const response = await fetch("http://localhost:8080/dellUser", {
                method: "DELETE",
                headers: {"Content-Type": "application/json"}
            });

            if (response.ok) {
                const response = await fetch("http://localhost:8080/logout", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"}
                });

                if (response.ok) {
                    window.location.href = "/";
                } else {
                    alert("Erro ao deslogar");
                }
            } else {
                alert("Erro ao deletar usuário");
                closeAll();
            }
        } else {
            campoSenhaDell.classList.add("hidden");
            telaErroDell.classList.remove("hidden");
            senha.value = ``;
        }
    });

    btnCanDell.addEventListener("click", function () {
        closeAll();
    });

    btnTenNovDell.addEventListener("click", function () {
        telaErroDell.classList.add("hidden");
        campoSenhaDell.classList.remove("hidden");
    });

    document.getElementById("loginForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const login = document.querySelector("#userNameGet").value;
        const senha = document.getElementById("txtSenha").value;
        const senhaO = document.getElementById("senhaOculta");

        if (!senha) {
            alert("Por favor, insira uma senha.");
            return;
        }

        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({login, senha})
        });

        if (response.ok) {
            campoSenha.classList.add("hidden");
            selecionarFunc.classList.remove("hidden");
            senhaO.value = `${senha}`;
            senha.value = ``;
        } else {
            campoSenha.classList.add("hidden");
            telaErro.classList.remove("hidden");
            senha.value = ``;
        }
    });

    document.getElementById("editarSenha").addEventListener("submit", async function (e) {
        e.preventDefault();
        const senha = document.getElementById("novaSenha").value;
        const login = document.getElementById("loginOculta").value;

        const response = await fetch("http://localhost:8080/editarUser", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({login, senha})
        });

        if (response.ok) {
            userCampo.classList.add("hidden");
            telaFim.classList.remove("hidden");
            senha.value = ``;
        } else {
            login.value = ``;
            alert("Erro inesperado");

            const response = await fetch("http://localhost:8080/logout", {
                method: "POST",
                headers: {"Content-Type": "application/json"}
            });

            if (response.ok) {
                window.location.href = "/";
            } else {
                alert("Erro ao deslogar");
            }
        }
    });

    document.getElementById("editarLogin").addEventListener("submit", async function (e) {
        e.preventDefault();
        const senha = document.getElementById("senhaOculta").value;
        const login = document.getElementById("novoLogin").value;

        const response = await fetch("http://localhost:8080/editarUser", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({login, senha})
        });

        if (response.ok) {
            userCampo.classList.add("hidden");
            telaFim.classList.remove("hidden");
            login.value = ``;
        } else {
            login.value = ``;
            alert("Erro inesperado");

            const response = await fetch("http://localhost:8080/logout", {
                method: "POST",
                headers: {"Content-Type": "application/json"}
            });

            if (response.ok) {
                window.location.href = "/";
            } else {
                alert("Erro ao deslogar");
            }
        }
    });

    btnDeslog.addEventListener("click", async function () {
        const response = await fetch("http://localhost:8080/logout", {
            method: "POST",
            headers: {"Content-Type": "application/json"}
        });

        if (response.ok) {
            window.location.href = "/";
        } else {
            alert("Erro ao deslogar");
        }
    });

    btnFinish.addEventListener("click", async function () {
        const response = await fetch("http://localhost:8080/logout", {
            method: "POST",
            headers: {"Content-Type": "application/json"}
        });

        if (response.ok) {
            window.location.href = "/";
        } else {
            alert("Erro ao deslogar");
        }
    });

    btnOpen.addEventListener("click", function () {
        menuUser.classList.remove("hidden");
    });

    menuClose.addEventListener("click", function () {
        menuUser.classList.add("hidden");
    });

    btnEditUser.addEventListener("click", function () {
        userCampo.classList.remove("hidden");
        telaEdit.classList.remove("hidden");
        campoSenha.classList.remove("hidden");
    });

    editClose.addEventListener("click", function () {
        closeAll();
    });

    btnUser.addEventListener("click", function () {
        selecionarFunc.classList.add("hidden");
        editUser.classList.remove("hidden");
    });

    btnSenha.addEventListener("click", function () {
        selecionarFunc.classList.add("hidden");
        editSenha.classList.remove("hidden");
    });

    btnCan.addEventListener("click", function () {
        closeAll();
    });

    btnTenNov.addEventListener("click", function () {
        telaErro.classList.add("hidden");
        campoSenha.classList.remove("hidden");
    });
});