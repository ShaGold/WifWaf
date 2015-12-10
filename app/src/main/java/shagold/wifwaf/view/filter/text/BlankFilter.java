package shagold.wifwaf.view.filter.text;

import android.widget.EditText;

import shagold.wifwaf.view.ErrorMessage;
import shagold.wifwaf.view.ValidateMessage;

/**
 * Created by jimmy on 29/11/15.
 */
public class BlankFilter extends EditTextFilter {

    @Override
    public ValidateMessage meetFilter(EditText text) {

        String s = text.getText().toString();

        if("".equals(s))
            return new ValidateMessage(ErrorMessage.BLANK);
        else
            return new ValidateMessage();
    }

}
