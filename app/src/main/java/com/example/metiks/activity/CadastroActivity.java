package com.example.metiks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.metiks.R;
import com.example.metiks.helper.ConfiguracaoFirebase;
import com.example.metiks.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializarComponentes();

        //cadastrar Usuario
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //verifica se algum campo esta vazio
                if ( !textoNome.isEmpty()){
                    if ( !textoEmail.isEmpty()){
                        if ( !textoSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setEmail(textoEmail);
                            usuario.setNome(textoNome);
                            usuario.setSenha(textoSenha);

                            cadastrarUsuario( usuario);

                        }
                        else {
                            Toast.makeText(CadastroActivity.this, "Preencha a Senha" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CadastroActivity.this, "Preencha o Email" , Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CadastroActivity.this, "Preencha o nome" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //metodo responsavel por cadastrar usuario e fazer validaçoes
    public void cadastrarUsuario(Usuario usuario){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastrado com sucesso",
                                    Toast.LENGTH_SHORT).show();

                            //direciona para MainActivity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else {
                            progressBar.setVisibility(View.GONE);

                            String erroExecao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExecao = "Digite uma senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExecao = "Digite um email válido!";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExecao = "essa conta ja foi cadastrad";
                            }catch (Exception e){
                                erroExecao = "ao Cadastrar Usuario: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    "Erro: " + erroExecao ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


    }

    public void inicializarComponentes(){
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editcadastroPassword);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
        progressBar = findViewById(R.id.progressCadastrar);


    }


}