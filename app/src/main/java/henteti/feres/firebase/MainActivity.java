package henteti.feres.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "main";

    private TextInputEditText nom;
    private TextInputEditText prenom;
    private TextInputEditText mail;
    private TextInputEditText motpasse;
    private TextInputEditText telephone;
    private TextInputEditText adresse;
    private TextInputEditText codepostal;
    private TextInputEditText societe;
    private FloatingActionButton fab;

    //références BD
    private DatabaseReference database;
    private DatabaseReference usersRef;

    //pour l'authentification
    private FirebaseAuth mAuth;

    //objet user qu'on va utiliser
    private User user;

    //objet user connecté sur firebase
    private FirebaseUser fbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("inscription");
        setSupportActionBar(toolbar);

        initGraphics();
        initFirebase();

    }

    private void initFirebase() {

        database = FirebaseDatabase.getInstance().getReference();
        usersRef = database.child("users");
        mAuth = FirebaseAuth.getInstance();

    }

    private void initGraphics() {
        nom=(TextInputEditText) findViewById(R.id.txtname);
        prenom=(TextInputEditText) findViewById(R.id.txtfamilyname);
        mail=(TextInputEditText) findViewById(R.id.txtmail);
        motpasse=(TextInputEditText) findViewById(R.id.txtpwd);
        telephone=(TextInputEditText) findViewById(R.id.txttel);
        adresse=(TextInputEditText) findViewById(R.id.txtadr);
        codepostal=(TextInputEditText) findViewById(R.id.txtcodepostal);
        societe=(TextInputEditText) findViewById(R.id.txtsocite);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.fab){

            user = new User();
            user.setNom(nom.getText().toString());
            user.setPrenom(prenom.getText().toString());
            user.setAdresse(adresse.getText().toString());
            user.setCodepostal(codepostal.getText().toString());
            user.setMail(mail.getText().toString());
            user.setMotpasse(motpasse.getText().toString());
            user.setTelephone(telephone.getText().toString());
            user.setSociete(societe.getText().toString());

            //inscrire le nouvel utilisateur dans la table user de firebase
            // pour pouvoir se connecter ultérieurement avec son email et mot de passe
            mAuth.createUserWithEmailAndPassword(user.getMail().toString(), user.getMotpasse().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                // en cas d'erreur
                                Snackbar.make(fab, "erreur !", Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                Snackbar.make(fab, "utilisateur bien ajouté..", Snackbar.LENGTH_LONG).show();

                                //maintenant on fait l'authentification avec l'email et le mot de passe
                                // de cet utilisateur pour ajouter ses données à la base de données
                                seConnecter(user.getMail().toString(), user.getMotpasse().toString());
                            }

                        }
                    });


        }


    }

    private void seConnecter(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // en cas d'erreur
                        if (!task.isSuccessful()) {
                            Snackbar.make(fab, "erreur !", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Snackbar.make(fab, "connecté", Snackbar.LENGTH_LONG).show();

                            //on prend l'objet user qui est maintenant connecté à Firebase
                            // pour utiliser son ID de Firebase
                            fbUser = FirebaseAuth.getInstance().getCurrentUser();

                            //ensuite on ajoute les données de cet utilisateur à la base de données
                            // suivant son ID
                            if (fbUser != null){
                                //Ajouter les données de l'utilisateur à la base de données
                                Map<String,Object> userMap = new HashMap<>();
                                userMap.put(fbUser.getUid(), user);
                                usersRef.updateChildren(userMap);
                            }

                        }
                    }
                });

    }


}
