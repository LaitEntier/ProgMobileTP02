package com.example.tp02calculmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Récupère les vues dont on a besoin
        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);

        //Attribue les listeners aux vues qui en ont besoin
        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);
    }

    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            //Récupère la taille
            String t = taille.getText().toString();
            //Récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);

            if (t.contains(".")) {
            //Utiliser setChecked(boolean checked) ou toggle()
            }
            //V&rifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, "La taille doit être positive", Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if (pValue <= 0)
                    Toast.makeText(MainActivity.this, "Le poids doit être positif", Toast.LENGTH_SHORT).show();
                else {
                    //Si user a indiqué que la taille est en cm
                    //Vérifie que la CheckBox sélectionnée est la 2e à l'aide de son id
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre)
                        tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat = "Votre IMC est "+imc+".";
                    if (commentaire.isChecked())
                        resultat += interpreteIMC(imc);
                    result.setText(resultat);
                }
            }
        }
        public String interpreteIMC(float f) {
            if (f < 16.5) {
                return "\nFamine";
            }
            else if (f >= 16.5 && f < 18.5) {
                return "\nMaigreur";
            }
            else if (f >= 18.5 && f < 25) {
                return "\nCorpulence normale";
            }
            else if (f >= 25 && f < 30) {
                return "\nSurpoids";
            }
            else if (f >= 30 && f < 35) {
                return "\nObésité modérée";
            }
            else if (f >= 35 && f < 40) {
                return "\nObésité sévère";
            }
            return "\nObésité morbide ou sévère";
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()){
                result.setText(texteInit);
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Exercice 2 - Change automatiquement le bouton radio quand on détecte la présence d’un point décimal dans la saisie de la taille.
            if(taille.getText().toString().contains(".") && group.getCheckedRadioButtonId() == R.id.radio_centimetre)
                group.check(R.id.radio_metre);
            result.setText(texteInit);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

}