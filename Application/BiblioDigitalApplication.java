
package application;
import java.util.List;
import java.util.Scanner;
import model.Livro;
import model.LivroFisico;
import model.LivroDigital;
import model.AudioLivro;
import repository.LivroRepository;
import service.LivroService;

public class BiblioDigitalApplication {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        LivroRepository repository = new LivroRepository();
        LivroService service = new LivroService(repository);
        int opcao;
        do {
            mostrarMenu();

            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarLivro(service);
                        break;

                    case 2:
                        pesquisarPorTitulo(service);
                        break;

                    case 3:
                        pesquisarPorAutor(service);
                        break;

                    case 4:
                        alterarLivro(service);
                        break;

                    case 5:
                        excluirLivro(service);
                        break;

                    case 0:
                        System.out.println("Até logo!");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void mostrarMenu() {

        System.out.println("========== BIBLIODIGITAL ==========");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Pesquisar livro por título");
        System.out.println("3 - Pesquisar livro por autor");
        System.out.println("4 - Alterar dados do livro");
        System.out.println("5 - Excluir livro");
        System.out.println("0 - Sair");
        System.out.println("===================================");
    }

    private static void cadastrarLivro(LivroService service) {

        System.out.println("Qual tipo de livro deseja cadastrar?");
        System.out.println("1 - Livro físico");
        System.out.println("2 - Livro digital");
        System.out.println("3 - Audiolivro");

        int tipo = lerInteiro("Escolha uma opção: ");

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de livro inválido!");
            return;
        }

        String titulo = lerTexto("Digite o título: ");
        String autor = lerTexto("Digite o autor: ");
        int ano = lerInteiro("Digite o ano de publicação: ");

        Livro livro;

        if (tipo == 1) {

            int quantidade = lerInteiro("Quantidade de exemplares: ");
            String localizacao = lerTexto("Localização: ");

            livro = new LivroFisico(titulo, autor, ano, quantidade, localizacao);

        } else if (tipo == 2) {

            String formato = lerTexto("Formato do arquivo: ");
            double tamanho = lerDouble("Tamanho do arquivo em MB: ");

            livro = new LivroDigital(titulo, autor, ano, formato, tamanho);

        } else {

            int duracao = lerInteiro("Duração em minutos: ");
            String narrador = lerTexto("Nome do narrador: ");

            livro = new AudioLivro(titulo, autor, ano, duracao, narrador);
        }

        service.cadastrar(livro);

        System.out.println("Livro cadastrado com sucesso!");
    }

    private static void pesquisarPorTitulo(LivroService service) {

        String titulo = lerTexto("Digite o título ou parte dele: ");

        List<Livro> livros = service.buscarPorTitulo(titulo);

        exibirLivros(livros);
    }

    private static void pesquisarPorAutor(LivroService service) {

        String autor = lerTexto("Digite o nome ou parte do autor: ");

        List<Livro> livros = service.buscarPorAutor(autor);

        exibirLivros(livros);
    }

    private static void alterarLivro(LivroService service) {

        List<Livro> livros = service.listarTodos();

        if (livros.isEmpty()) {
            System.out.println("Não existem livros cadastrados.");
            return;
        }

        exibirLivrosNumerados(livros);

        int numero = lerInteiro("Escolha o número do livro: ");

        if (numero < 1 || numero > livros.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Livro original = livros.get(numero - 1);

        String titulo = lerTexto("Novo título: ");
        String autor = lerTexto("Novo autor: ");
        int ano = lerInteiro("Novo ano: ");

        Livro atualizado;

        if (original instanceof LivroFisico) {

            int quantidade = lerInteiro("Nova quantidade: ");
            String localizacao = lerTexto("Nova localização: ");

            atualizado = new LivroFisico(titulo, autor, ano, quantidade, localizacao);

        } else if (original instanceof LivroDigital) {

            String formato = lerTexto("Novo formato: ");
            double tamanho = lerDouble("Novo tamanho em MB: ");

            atualizado = new LivroDigital(titulo, autor, ano, formato, tamanho);

        } else {

            int duracao = lerInteiro("Nova duração em minutos: ");
            String narrador = lerTexto("Novo narrador: ");

            atualizado = new AudioLivro(titulo, autor, ano, duracao, narrador);
        }

        service.atualizar(original.getTitulo(), atualizado);

        System.out.println("Livro atualizado com sucesso!");
    }

    private static void excluirLivro(LivroService service) {

        List<Livro> livros = service.listarTodos();

        if (livros.isEmpty()) {
            System.out.println("Não existem livros cadastrados.");
            return;
        }

        exibirLivrosNumerados(livros);

        int numero = lerInteiro("Escolha o número do livro: ");

        if (numero < 1 || numero > livros.size()) {
            System.out.println("Número inválido!");
            return;
        }

        Livro livro = livros.get(numero - 1);

        System.out.println(livro);

        String resposta = lerTexto("Deseja excluir? (S/N): ");

        if (resposta.equalsIgnoreCase("S")) {
            service.excluir(livro.getTitulo());
            System.out.println("Livro excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static void exibirLivros(List<Livro> livros) {

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    private static void exibirLivrosNumerados(List<Livro> livros) {

        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + " - " + livros.get(i));
        }
    }

    private static String lerTexto(String mensagem) {

        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {

        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido!");
            }
        }
    }

    private static double lerDouble(String mensagem) {

        while (true) {
            try {
                System.out.print(mensagem);

                String valor = scanner.nextLine().replace(",", ".");

                return Double.parseDouble(valor);

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido!");
            }
        }
    }
}
