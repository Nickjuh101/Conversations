package eu.siacs.conversations.ui.forms;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import eu.siacs.conversations.xmpp.forms.Data;
import eu.siacs.conversations.xmpp.forms.Field;

public class FormWrapper {

	private final LinearLayout layout;

	private final Data form;

	private final List<FormFieldWrapper> fieldWrappers = new ArrayList<>();

	private FormWrapper(Context context, LinearLayout linearLayout, Data form) {
		this.form = form;
		this.layout = linearLayout;
		this.layout.removeAllViews();
		for(Field field : form.getFields()) {
			FormFieldWrapper fieldWrapper = FormFieldFactory.createFromField(context,field);
			if (fieldWrapper != null) {
				layout.addView(fieldWrapper.getView());
				fieldWrappers.add(fieldWrapper);
			}
		}
	}
}
