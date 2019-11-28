package br.com.ifsp.aluno.allex.taskly.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

public class InputDialog {

    private AlertDialog.Builder dialogBuilder;
    private final EditText edtInput;

    public InputDialog(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);

        edtInput = new EditText(context);
        edtInput.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogBuilder.setView(edtInput);
    }

    public InputDialog setTitle(String title) {
        dialogBuilder.setTitle(title);
        return this;
    }

    public InputDialog setPlaceholder(String placeholder) {
        edtInput.setHint(placeholder);
        return this;
    }

    public InputDialog setPositiveButton(String text, DialogInterface.OnClickListener listener) {
        dialogBuilder.setPositiveButton(text, listener);
        return this;
    }

    public InputDialog setNegativeButton(String text, DialogInterface.OnClickListener listener) {
        dialogBuilder.setNegativeButton(text, listener);
        return this;
    }

    public void show() {
        dialogBuilder.show();
    }

    public String getValue() {
        return edtInput.getText().toString();
    }
}
