 function submitForm() {
        const formData = {
            nome: document.getElementById('nome').value,
            urlImagem: document.getElementById('urlImagem').value,
            preco: parseFloat(document.getElementById('preco').value),
            descricao: document.getElementById('descricao').value,
            cuidados: document.getElementById('cuidados').value,
            cor: document.getElementById('cor').value,
            disponibilidade: document.getElementById('disponibilidade').value,
            embalagem: document.getElementById('embalagem').value
        };

        fetch('/flores', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                alert('Produto adicionado com sucesso!');
                window.location.reload(); // Recarrega a página para atualizar a lista de produtos
            } else {
                alert('Erro ao adicionar produto.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
        });
    }