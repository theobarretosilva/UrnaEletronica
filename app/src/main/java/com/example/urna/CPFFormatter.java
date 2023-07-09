package com.example.urna;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CPFFormatter implements TextWatcher {
    private static final String MASK_CPF = "###.###.###-##";
    private static final String DIGIT_MASK = "#";

    private final EditText editText;
    private boolean isUpdating;
    private String oldText;

    public CPFFormatter(EditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Não é necessário implementar
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Não é necessário implementar
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        String newText = s.toString();

        String unmaskedText = newText.replaceAll("[^0-9]", "");

        String maskedText = applyMask(unmaskedText);

        if (!newText.equals(maskedText)) {
            isUpdating = true;
            editText.setText(maskedText);
            editText.setSelection(maskedText.length());
        }
    }

    private String applyMask(String text) {
        StringBuilder maskedText = new StringBuilder();
        int maskIndex = 0;
        int textIndex = 0;

        while (maskIndex < MASK_CPF.length() && textIndex < text.length()) {
            if (MASK_CPF.charAt(maskIndex) == DIGIT_MASK.charAt(0)) {
                maskedText.append(text.charAt(textIndex));
                textIndex++;
            } else {
                maskedText.append(MASK_CPF.charAt(maskIndex));
            }
            maskIndex++;
        }

        return maskedText.toString();
    }
}
