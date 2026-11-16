
package service;
import java.util.List;
import model.Livro;
import repository.LivroRepository;

public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    private void validarLivro(Livro livro) {

        if (livro == null) {
            throw new IllegalArgumentException("Livro inválido.");
        }

        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ficar vazio.");
        }

        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ficar vazio.");
        }

        if (livro.getAnoPublicacao() < 1900) {
            throw new IllegalArgumentException("O ano deve ser igual ou superior a 1900.");
        }

        livro.setTitulo(livro.getTitulo().trim());
        livro.setAutor(livro.getAutor().trim());
    }

    public void cadastrar(Livro livro) {

        validarLivro(livro);

        for (Livro existente : repository.listarTodos()) {

            if (existente.getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                throw new IllegalArgumentException("Já existe um livro com esse título.");
            }
        }

        repository.cadastrar(livro);
    }


    public List<Livro> buscarPorTitulo(String titulo) {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Digite um título para pesquisar.");
        }

        return repository.buscarPorTituloParcial(titulo.trim());
    }


    public List<Livro> buscarPorAutor(String autor) {

        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("Digite um autor para pesquisar.");
        }

        return repository.buscarPorAutor(autor.trim());
    }

    public List<Livro> listarTodos() {
        return repository.listarTodos();
    }
    public void atualizar(String tituloOriginal, Livro livroAtualizado) {

        validarLivro(livroAtualizado);

        for (Livro existente : repository.listarTodos()) {

            if (existente.getTitulo().equalsIgnoreCase(livroAtualizado.getTitulo())
                    && !existente.getTitulo().equalsIgnoreCase(tituloOriginal)) {

                throw new IllegalArgumentException("Já existe outro livro com esse título.");
            }
        }

        boolean atualizado = repository.atualizar(tituloOriginal, livroAtualizado);

        if (!atualizado) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
    }

    public void excluir(String titulo) {

        boolean excluido = repository.excluir(titulo);

        if (!excluido) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
    }
}
