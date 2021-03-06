package com.github.ilyes4j.gwt.mdl.demo.modules.textfields;

import java.util.ArrayList;
import java.util.Collection;

import com.github.ilyes4j.gwt.mdl.demo.utils.MdlGwtDemoUtils;
import com.google.gwt.core.client.EntryPoint;

/**
 * {@link EntryPoint} containing the GWT part of the text fields demos.
 * 
 * @author Mohamed Ilyes DIMASSI
 */
public class TextfieldDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public final void onModuleLoad() {

    Collection<EntryPoint> demos = new ArrayList<>();
    demos.add(new DefaultTextfieldDemo());
    demos.add(new FloatTextfieldDemo());
    demos.add(new ValidateTextfieldDemo());
    demos.add(new NumberTextfieldDemo());
    demos.add(new AreaTextfieldDemo());
    demos.add(new ExpandingTextfieldDemo());
    demos.add(new PasswordFieldDemo());
    demos.add(new SetTextDemo());
    demos.add(new TextfieldChangeDemo());
    demos.add(new TextfieldKeyPressDemo());

    for (EntryPoint entry : demos) {
      entry.onModuleLoad();
    }

    MdlGwtDemoUtils.insertMainbar();
    MdlGwtDemoUtils.insertComponentsNavbar();
  }
}
