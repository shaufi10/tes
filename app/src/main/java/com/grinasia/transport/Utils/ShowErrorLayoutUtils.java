package com.grinasia.transport.Utils;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by coder on 24-Jan-17.
 */

public class ShowErrorLayoutUtils {
    public static void showValidationErrorMessage(TextInputLayout textInputLayout, EditText editText, String errorMessage) {
        textInputLayout.setErrorEnabled(true);

        textInputLayout.setError(errorMessage);
        editText.setError(errorMessage);
    }
}
