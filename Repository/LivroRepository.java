
package repository;
import java.util.ArrayList;
import java.util.List;
import model.Livro;
public class LivroRepository {

    private List<Livro> livros = new ArrayList<>();


    public void cadastrar(Livro livro) {
        livros.add(livro);
    }

  
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }


    public List<Livro> buscarPorTituloParcial(String titulo) {

        List<Livro> encontrados = new ArrayList<>();

        for (Livro livro : livros) {

            if (livro.getTitulo().toLowerCase()
                    .contains(titulo.toLowerCase())) {

                encontrados.add(livro);
            }
        }

        return encontrados;
    }

    public List<Livro> buscarPorAutor(String autor) {

        List<Livro> encontrados = new ArrayList<>();

        for (Livro livro : livros) {

            if (livro.getAutor().toLowerCase()
                    .contains(autor.toLowerCase())) {

                encontrados.add(livro);
            }
        }

        return encontrados;
    }

    public boolean atualizar(String tituloOriginal, Livro livroAtualizado) {

        for (int i = 0; i < livros.size(); i++) {

            if (livros.get(i).getTitulo().equalsIgnoreCase(tituloOriginal)) {

                livros.set(i, livroAtualizado);
                return true;
            }
        }

        return false;
    }

    public boolean excluir(String titulo) {

        for (int i = 0; i < livros.size(); i++) {

            if (livros.get(i).getTitulo().equalsIgnoreCase(titulo)) {

                livros.remove(i);
                return true;
            }
        }

        return false;
    }
}
