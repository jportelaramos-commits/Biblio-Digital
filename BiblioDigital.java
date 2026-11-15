
void main() {
    List <Livro> livros = new ArrayList<>();

    int opcao;
    do{
        mostrarMenu();
        opcao = Integer.parseInt(IO.readln("Digite a opção desejada: "));
        switch(opcao) {
            case 1 -> cadastrarLivro(livros);
            case 2 -> imprimirLivros(livros);
            case 0 -> IO.println("Saindo do programa");
            default -> IO.println("Opção inválida");    
        }
        IO.readln("Pressione ENTER para continuar...");
    } while (opcao != 0);
}

void mostrarMenu() {
    IO.println("""
    ========== BIBLIODIGITAL ==========
    1 - Cadastrar livro
    2 - Listar livros
    0 - Sair
    ===================================
    """);
}

void cadastrarLivro(List<Livro> livros) {
    String tipoLivro = """
    Escolha o tipo de livro que deseja cadastrar:
    1 - Livro Físico
    2 - Livro Digital
    3 - Livro Audio 

    Escolha a opção desejada:
    """;
    int opcao = Integer.parseInt(IO.readln(tipoLivro));
    if (opcao <1 || opcao >3) {
        IO.println("Opção inválida");
        return;
    }

    Livro novoLivro;
    String titulo = IO.readln("Digite o título do livro: ");
    String autor = IO.readln("Digite o autor do livro: ");
    int anoPublicacao = Integer.parseInt(IO.readln("Digite o ano de publicação do livro: "));
    if (opcao == 1) {
        int quantidadeExemplares = Integer.parseInt(IO.readln("Digite o número de exemplares: "));
        String localizacao = IO.readln("Digite a localização: ");
        novoLivro = new LivroFisico(titulo, autor, anoPublicacao, quantidadeExemplares, localizacao);
    } else if (opcao == 2) {
        String formato = IO.readln("Digite o formato do arquivo: ");
        double tamanhoArquivo = Double.parseDouble(IO.readln("Digite o tamanho do arquivo: "));        
        novoLivro = new LivroDigital(titulo, autor, anoPublicacao, formato, tamanhoArquivo);
    } else {
        int duracao = Integer.parseInt(IO.readln("Digite a duração do audiolivro: "));
        String narrador = IO.readln("Digite o nome do narrador: ");
        novoLivro = new AudioLivro(titulo, autor, anoPublicacao, duracao, narrador);
    }  
    livros.add(novoLivro);
}

void imprimirLivros(List<Livro> livros) {
    if (livros.isEmpty()) {
        IO.println("Nenhum livro cadastrado.");
        return;
    }
    for (Livro livro : livros) {
        IO.println(livro);
    }
}