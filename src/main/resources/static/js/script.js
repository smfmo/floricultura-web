const produtos = [
    //Array de produtos
    {nome: "Rosa Vermelha", preco: 10.00, imagem: "/src/img/buque.png"},
    {nome: "Buque", preco: 10.00, imagem: "/src/img/samambaia.jpg"},
    {nome: "Rosa Vermelha", preco: 10.00, imagem: "/src/img/rosa.jpg"},
    {nome: "Rosa Vermelha", preco: 10.00, imagem: "/src/img/samambaia.jpg"},
    {nome: "rosa branca", preco:12.00, imagem:"/src/img/samambaia.jpg"}

]
const lista = document.getElementById("product-list");

lista.innerHTML = "";

const produtosUnicos = produtos.filter((produto, index, self) =>
    index === self.findIndex((p) => p.nome === produto.nome && p.imagem === produto.imagem)
);

//Função para renderizar os produtos 
function renderizarProdutos(){
    produtos.forEach((produto,index) => {
        const card = `
            <div class="col-md-3 col-sm-6 mb-4">
                        <div class="d-flex flex-wrap" style="gap: ">
                           
                                <div class="card" style="width: 14rem; margin:auto;">
                                <img src="${produto.imagem}"  class="card-img-top img-fluid" alt="${produto.nome} " style="height: auto; max-width: 100%px; text-align:center; ">
                                <div class="card-body">
                                    <h5 class="card-title">${produto.nome}</h5>
                                    <p class="card-text">A partir de R$ ${produto.preco.toFixed(2)}</p>
                                    <button type="submit" class="btn btn-primary"  onclick="redirecionarCompra()">Comprar</button>
                                    <button type="submit" class="btn btn-primary"  onclick="redirecionarDescricao()">detalhes</button>

                                </div>   
                        </div>   
                </div> `
            lista.innerHTML += card;
       
    });
}

function adicionarCarrinho(index){
    const produto = produto[index]
    console.log(`produto adicionado ao carrinho: ${produto.nome}`); //função para adicionar no carrinho
}

renderizarProdutos();
adicionarCarrinho();

//passar pra proxima pagina
function redirecionarCompra(){
    window.location.href = "http://127.0.0.1:5500/src/Compra.html";
}

function redirecionarDescricao(){
    window.location.href = "http://127.0.0.1:5500/src/Descricao.html";
}

function redirecionarHome(){
    window.location.href = "/floricultura/src/main/resources/templates/catalogo.html";
}


