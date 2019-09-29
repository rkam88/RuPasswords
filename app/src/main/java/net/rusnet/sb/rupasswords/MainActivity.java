package net.rusnet.sb.rupasswords;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView resultTextView;
    private View copyButton;
    private EditText sourceEditText;

    private TextView passwordStrengthTextView;
    private ImageView passwordStrengthIndicator;
    private PasswordStrengthChecker checker;


    private CompoundButton checkCaps;
    private CompoundButton checkDigits;
    private CompoundButton checkSymbols;

    private SeekBar lengthSeekBar;
    private int totalLength;
    private TextView lengthText;

    private TextView generatedTextView;
    private View copyGeneratedButton;
    private View generateButton;
    private PasswordGenerator generator;



    private String[] russians;
    private String[] latin;
    private PasswordsHelper helper;


     //стараться всегда испльзовать максимально абстрактный тип

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.text_result); //поле вывода конвертированного пароля
        copyButton = findViewById(R.id.button_copy_password); //кнопка копирования
        sourceEditText = findViewById(R.id.edit_source); //поле ввода пароля на русском

        passwordStrengthTextView = findViewById(R.id.password_strength); //подсказка силы пароля
        passwordStrengthIndicator = findViewById(R.id.password_strength_hint); //цветовой индикато силы пароля
        checker = new PasswordStrengthChecker();

        checkCaps = findViewById(R.id.check_caps);
        checkDigits = findViewById(R.id.check_digits);
        checkSymbols = findViewById(R.id.check_symbols);

        generatedTextView = findViewById(R.id.text_generated);
        copyGeneratedButton = findViewById(R.id.button_copy_generated);
        generateButton = findViewById(R.id.button_generate_password);
        generator = new PasswordGenerator();

        russians = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);

        helper = new PasswordsHelper(russians, latin);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (manager != null) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_tittle), resultTextView.getText().toString()
                            )
                    );
                }
                Toast.makeText(MainActivity.this, R.string.toast_copied, Toast.LENGTH_SHORT).show();
            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String convertedPassword = helper.convert(charSequence);

                resultTextView.setText(convertedPassword);
                copyButton.setEnabled(charSequence.length() > 0);

                PasswordStrength strength = checker.checkPasswordStrength(convertedPassword);
                switch (strength) {
                    case EMPTY:
                        passwordStrengthTextView.setText(R.string.password_strength_initial);
                        passwordStrengthIndicator.getDrawable().setLevel(0);
                        break;
                    case LOW:
                        passwordStrengthTextView.setText(R.string.password_strength_low);
                        passwordStrengthIndicator.getDrawable().setLevel(2500);
                        break;
                    case MEDIUM:
                        passwordStrengthTextView.setText(R.string.password_strength_medium);
                        passwordStrengthIndicator.getDrawable().setLevel(5000);
                        break;
                    case NORMAL:
                        passwordStrengthTextView.setText(R.string.password_strength_normal);
                        passwordStrengthIndicator.getDrawable().setLevel(7000);
                        break;
                    case GOOD:
                        passwordStrengthTextView.setText(R.string.password_strength_good);
                        passwordStrengthIndicator.getDrawable().setLevel(8500);
                        break;
                    case GREAT:
                        passwordStrengthTextView.setText(R.string.password_strength_great);
                        passwordStrengthIndicator.getDrawable().setLevel(10000);
                        break;
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lengthSeekBar = findViewById(R.id.seekbar_password_length);
        lengthText = findViewById(R.id.text_length);
        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int addToLength = progress;
                totalLength = 8 + addToLength;

                String display = getString(R.string.symbols) +
                        ": 8 + " +
                        getResources().getQuantityString(R.plurals.symbols_count,addToLength,addToLength) +
                        " = " +
                        getResources().getQuantityString(R.plurals.symbols_count,totalLength,totalLength);
                lengthText.setText(display);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        lengthSeekBar.setProgress(0);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generatedPassword;
                generatedPassword = generator.generatePassword(
                        totalLength,
                        checkCaps.isChecked(),
                        checkDigits.isChecked(),
                        checkSymbols.isChecked()
                );
                generatedTextView.setText(generatedPassword);
            }
        });

        copyGeneratedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (manager != null) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_tittle), generatedTextView.getText().toString()
                            )
                    );
                }
                Toast.makeText(MainActivity.this, R.string.toast_copied, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
