package com.example.listapersonagens.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagens.R;
import com.example.listapersonagens.dao.PersonagemDAO;
import com.example.listapersonagens.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagens.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;


public class FormularioPersonagemActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem";
    private static final String TITULO_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText nomePersonagem;
    private EditText alturaPersonagem;
    private EditText nascimentoPersonagem;
    private EditText telefonePersonagem;
    private EditText enderecoPersonagem;
    private EditText cepPersonagem;
    private EditText rgPersonagem;
    private EditText generoPersonagem;

    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Icone "check" para salvar os personagens
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click do "check" para salvar a galera
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);

        inicializacaoCampos();
        configuraBotao();
        carregaPersonagem();
    }

    private void carregaPersonagem() {
        //Carrega info dos personagens
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencherCampos();
        } else {
            setTitle(TITULO_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    private void preencherCampos() {
        //Set info
        nomePersonagem.setText(personagem.getNome());
        alturaPersonagem.setText(personagem.getAltura());
        nascimentoPersonagem.setText(personagem.getNascimento());
        telefonePersonagem.setText(personagem.getTelefone());
        enderecoPersonagem.setText(personagem.getEndereco());
        cepPersonagem.setText(personagem.getCep());
        rgPersonagem.setText(personagem.getRg());
        generoPersonagem.setText(personagem.getGenero());
    }

    private void configuraBotao() {
        //Armazena info do botao de salvar
        Button btnSalvar = findViewById(R.id.button);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        preencherPersonagem();
        if (personagem.IdValido()) {
            dao.edita(personagem);
        } else {
            dao.salva(personagem);
        }
        finish();
    }

    private void inicializacaoCampos() {
        //Campos var
        nomePersonagem = findViewById(R.id.textInputNome);
        alturaPersonagem = findViewById(R.id.editTextAltura);
        nascimentoPersonagem = findViewById(R.id.editTextDate);
        telefonePersonagem = findViewById(R.id.editTextTelefone);
        enderecoPersonagem = findViewById(R.id.editTextEndereco);
        cepPersonagem = findViewById(R.id.editTextCep);
        rgPersonagem = findViewById(R.id.editTextRg);
        generoPersonagem = findViewById(R.id.editTextGenero);

        //Mascara - altura
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(alturaPersonagem, smfAltura);
        alturaPersonagem.addTextChangedListener(mtwAltura);

        //Mascara - nascimento
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(nascimentoPersonagem, smfNascimento);
        nascimentoPersonagem.addTextChangedListener(mtwNascimento);

        //Mascara - telefone
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(telefonePersonagem, smfTelefone);
        telefonePersonagem.addTextChangedListener(mtwTelefone);

        //Mascara - Cep
        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(cepPersonagem, smfCep);
        cepPersonagem.addTextChangedListener(mtwCep);

        //Mascara - Rg
        SimpleMaskFormatter smfRg = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher mtwRg = new MaskTextWatcher(rgPersonagem, smfRg);
        rgPersonagem.addTextChangedListener(mtwRg);
    }


    private void preencherPersonagem() {

        //Set novas strings
        String nome = nomePersonagem.getText().toString();
        String altura = alturaPersonagem.getText().toString();
        String nascimento = nascimentoPersonagem.getText().toString();
        String telefone = telefonePersonagem.getText().toString();
        String endereco = enderecoPersonagem.getText().toString();
        String cep = cepPersonagem.getText().toString();
        String rg = rgPersonagem.getText().toString();
        String genero = generoPersonagem.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setCep(cep);
        personagem.setRg(rg);
        personagem.setGenero(genero);
    }
}