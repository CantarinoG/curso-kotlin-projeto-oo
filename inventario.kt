abstract class Produto(
    var id: Int,
    var nome: String,
    var preco: Double,
    var quantidade: Int,
    var descricao: String
) {
     open fun exibirInformacoes(){
        println("ID: $id")
        println("Nome: $nome")
        println("Preço: $preco")
        println("Quantidade: $quantidade")
        println("Descrição: $descricao")
    }
}

class Jogo(
    id: Int,
    nome: String,
    preco: Double,
    quantidade: Int,
    descricao: String,
    var plataforma: String,
    var classificacaoEtaria: Int,
    var genero: String
) : Produto(id, nome, preco, quantidade, descricao) {
    override fun exibirInformacoes() {
     	super.exibirInformacoes()
        println("Plataforma: $plataforma")
        println("Classificação Etária: $classificacaoEtaria")
        println("Gênero: $genero")
    }
}

abstract class Eletronico(
    id: Int,
    nome: String,
    preco: Double,
    quantidade: Int,
    descricao: String,
    var marca: String,
    var modelo: String,
    var consumoEnergia: Double
) : Produto(id, nome, preco, quantidade, descricao) {
    override fun exibirInformacoes() {
        super.exibirInformacoes()
        println("Marca: $marca")
        println("Modelo: $modelo")
        println("Consumo de Energia: $consumoEnergia")
    }
}

class Celular(
    id: Int,
    nome: String,
    preco: Double,
    quantidade: Int,
    descricao: String,
    marca: String,
    modelo: String,
    consumoEnergia: Double,
    var tamanhoTela: Double,
    var armazenamento: Int,
    var camera: String
) : Eletronico(id, nome, preco, quantidade, descricao, marca, modelo, consumoEnergia) {
    override fun exibirInformacoes() {
        super.exibirInformacoes()
        println("Tamanho da Tela: $tamanhoTela")
        println("Armazenamento: $armazenamento")
        println("Câmera: $camera")
    }
}

class Computador(
    id: Int,
    nome: String,
    preco: Double,
    quantidade: Int = 0,
    descricao: String,
    marca: String,
    modelo: String,
    consumoEnergia: Double,
    var processador: String,
    var ram: Int,
    var sistemaOperacional: String
) : Eletronico(id, nome, preco, quantidade, descricao, marca, modelo, consumoEnergia) {
    override fun exibirInformacoes() {
        super.exibirInformacoes()
        println("Processador: $processador")
        println("Memória RAM: $ram")
        println("Sistema Operacional: $sistemaOperacional")
    }
}

abstract class Inventario {
    companion object {
        private val produtos = mutableListOf<Produto>()

        fun listarProdutos() {
            for (produto in produtos) {
                println("ID: ${produto.id}, Nome: ${produto.nome}, Preço: ${produto.preco}, Quantidade: ${produto.quantidade}")
            }
        }
        
        fun verProduto(id: Int) {
            val index = produtos.indexOfFirst {it.id == id}
            if (index == -1) {
                throw Exception("Não existe um item com o id especificado.")
            } 
            val produto = produtos[index]
            produto.exibirInformacoes()
        }
        
        fun adicionarProduto(produto: Produto) {
            val index = produtos.indexOfFirst { it.id == produto.id }
            if(index != -1) {
                throw Exception("Já existe um item com esse id.")
            } 
            produtos.add(produto)   
            println("O produto ${produto.nome} foi adicionado.")
        }

        fun editarProduto(id: Int, novoProduto: Produto) {
            var index = produtos.indexOfFirst { it.id == id }
            if (index == -1) {
                throw Exception("Não existe um item com o id especificado.")
            } 
            index = produtos.indexOfFirst { it.id == novoProduto.id }
            if(index != -1) {
                throw Exception("Já existe um item com esse id.")
            } 
            produtos[id] = novoProduto
            println("O produto foi alterado.")
        }

        fun removerProduto(id: Int) {
            val index = produtos.indexOfFirst { it.id == id }
            if (index == -1) {
                throw Exception("Não existe um item com o id especificado.")
            } 
            produtos.removeAll { it.id == id }
            println("O produto foi removido.")
        }
        
    }
}

fun main() {

    while (true) {
        println("\n\n===========================")
        println("Escolha uma opção:")
        println("1. Listar produtos")
        println("2. Ver produto")
        println("3. Cadastrar produto")
        println("4. Editar produto")
        println("5. Remover produto")
        println("0. Sair")

        var opcao = readLine()?.toIntOrNull() ?: -1
        
        when (opcao) {
            1 -> {
                println("\n\n===========================")
                Inventario.listarProdutos()
                }
            2 -> {
                println("\n\n===========================")
                println("Qual o id do produto?")
                var id = readLine()?.toIntOrNull() ?: -1
                try { Inventario.verProduto(id) }
                catch(e: Exception) {
                    println(e.message)
                    continue
                }
            }
            3 -> {
                println("\n\n===========================")
                try { 
                    val produto = preencherProduto()
                    Inventario.adicionarProduto(produto)
                 }
                catch(e: Exception) {
                    println(e.message)
                    continue
                }
            }
            4 -> {
                println("\n\n===========================")
                println("Qual o id do produto a ser alterado?")
                var id = readLine()?.toIntOrNull() ?: -1
                try { 
                    val produto = preencherProduto()
                    Inventario.editarProduto(id, produto)
                 }
                catch(e: Exception) {
                    println(e.message)
                    continue
                }
            }
            5 -> {
                println("\n\n===========================")
                println("Qual o id do produto?")
                var id = readLine()?.toIntOrNull() ?: -1
                try { Inventario.removerProduto(id) }
                catch(e: Exception) {
                    println(e.message)
                    continue
                }
            }
            0 -> {
                println("\n\n===========================")
                println("Saindo do programa.")
                break
            }
            else -> println("Opção inválida. Tente novamente.")
        }
    }
    
}

fun preencherProduto(): Produto {
    var opcao: Int
    do {
        println("Qual a categoria do produto?")
        println("1. Jogo")
        println("2. Celular")
        println("3. Computador")
        opcao = readLine()?.toIntOrNull() ?: -1
    } while(opcao < 1 || opcao > 3)
    println("Insira o ID:")
    var id = readLine()?.toIntOrNull() ?: -1
    println("Insira o nome:")
    val nome = readLine() ?: ""
    println("Insira o preço:")
    val preco = readLine()?.toDouble()?: 0.0
    println("Insira a quantidade:")
    val quantidade = readLine()?.toIntOrNull() ?: -1
    println("Insira a descrição:")
    val descricao = readLine() ?: ""
    if(opcao == 1){
        println("Insira a plataforma:")
        val plataforma = readLine() ?: ""
        println("Insira a classificação etária:")
        val classificacaoEtaria = readLine()?.toIntOrNull() ?: -1
        println("Insira o gênero:")
        val genero = readLine() ?: ""
        val jogo = Jogo(id, nome, preco, quantidade, descricao, plataforma, classificacaoEtaria, genero)
        return jogo
    } 
    println("Insira a marca:")
    val marca = readLine() ?: ""
    println("Insira o modelo:")
    val modelo = readLine() ?: ""
    println("Insira o consumo de energia:")
    val consumoEnergia = readLine()?.toDouble()?: 0.0
    if(opcao == 2) {
        println("Insira o tamanho da tela:")
        val tamanhoTela = readLine()?.toDouble()?: 0.0
        println("Insira o armazenamento:")
        val armazenamento = readLine()?.toIntOrNull() ?: -1
        println("Insira a camera:")
        val camera = readLine() ?: ""
        val celular = Celular(id, nome, preco, quantidade, descricao, marca, modelo, consumoEnergia, tamanhoTela, armazenamento, camera)
        return celular
    } 
    println("Insira o processador:")
    val processador = readLine() ?: ""
    println("Insira a quantidade de RAM:")
    val ram = readLine()?.toIntOrNull() ?: -1
    println("Insira o sistmea operacional:")
    val sistemaOperacional = readLine() ?: ""
    val computador = Computador(id, nome, preco, quantidade, descricao, marca, modelo, consumoEnergia, processador, ram, sistemaOperacional)
    return computador
}