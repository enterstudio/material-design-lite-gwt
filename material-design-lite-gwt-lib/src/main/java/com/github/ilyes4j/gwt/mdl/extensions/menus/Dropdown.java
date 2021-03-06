package com.github.ilyes4j.gwt.mdl.extensions.menus;

import static com.github.ilyes4j.gwt.mdl.components.MdlGwtUtils.assertIndex;
import static com.github.ilyes4j.gwt.mdl.components.buttons.Button.createRaised;
import static com.github.ilyes4j.gwt.mdl.components.buttons.ButtonColor.BTN_NO_COLOR;
import static com.github.ilyes4j.gwt.mdl.components.ripples.Ripple.HAS_RIPPLE;

import com.github.ilyes4j.gwt.mdl.components.buttons.Button;
import com.github.ilyes4j.gwt.mdl.components.menus.IMenuCombo;
import com.github.ilyes4j.gwt.mdl.components.menus.ItemClickEvent;
import com.github.ilyes4j.gwt.mdl.components.menus.Menu.ItemClickListener;
import com.github.ilyes4j.gwt.mdl.components.menus.MenuAnchor;
import com.github.ilyes4j.gwt.mdl.components.menus.MenuCombo;
import com.google.gwt.user.client.ui.Composite;

/**
 * Is a {@link MenuCombo} that has the ability to store the selected item. When
 * {@link Dropdown} is initially setup, the first enabled item is set to be the
 * selected item by default.
 * 
 * @author Mohamed Ilyes DIMASSI
 *
 */
public class Dropdown extends Composite implements IMenuCombo {

  /**
   * Setup a default dropdown with a raised action {@link Button} and a menu
   * anchored to the bottom-left of the action button.
   */
  public Dropdown() {

    button = createRaised(BTN_NO_COLOR, HAS_RIPPLE, N_A);

    init(button);
  }

  /**
   * @param enable
   *          see {@link MenuCombo#setEnabled(boolean)}
   */
  public void setEnabled(final boolean enable) {
    combo.setEnabled(enable);
  }

  /**
   * @return see {@link MenuCombo#isEnabled()}
   */
  public boolean isEnabled() {
    return combo.isEnabled();
  }

  @Override
  public void setEventSource(final Object inputSource) {
    combo.setEventSource(inputSource);
  }

  @Override
  public final void setAnchor(final MenuAnchor anchor) {
    combo.setAnchor(anchor);
  }

  @Override
  public final void clearMenu() {
    combo.clearMenu();
    setSelected(-1);
  }

  @Override
  public final void addItemClickListener(final ItemClickListener listener) {
    combo.addItemClickListener(listener);
  }

  @Override
  public final int getItemCount() {
    return combo.getItemCount();
  }

  @Override
  public final String getItemText(final int index) {
    return combo.getItemText(index);
  }

  @Override
  public final String getValue(final int index) {
    return combo.getValue(index);
  }

  @Override
  public final boolean setEnabled(final int index, final boolean enabled) {
    return combo.setEnabled(index, enabled);
  }

  @Override
  public final boolean isEnabled(final int index) {
    return combo.isEnabled(index);
  }

  @Override
  public final void addItem(final String text, final String value,
      final boolean enabled) {
    addItem(text, value, getItemCount(), enabled);
  }

  @Override
  public final void addItem(final String item, final boolean enabled) {
    addItem(item, item, enabled);
  }

  /**
   * Has the same effect as Dropdown.addItem(item , true).
   * 
   * @param item
   *          the value and text of the item to be added
   */
  public final void addItem(final String item) {
    addItem(item, true);
  }

  /**
   * Shortcut for {@link Dropdown#addItem(String, boolean)} that adds an enabled
   * item to the menu.
   * 
   * @param value
   *          the value of the item to be added.
   * 
   * @param text
   *          the text of the item to be added.
   */
  public final void addItem(final String text, final String value) {
    addItem(text, value, true);
  }

  @Override
  public void addItem(final String text, final String value, final int index,
      final boolean enabled) {
    combo.addItem(text, value, index, enabled);
    selectDefault(index);
  }

  /**
   * @return the index of the currently selected item
   */
  public final int getSelectedIndex() {
    return selectedIndex;
  }

  /**
   * Set a selected item of the dropdown programmatically. If the chosen item is
   * disabled, the element is not selected.
   * 
   * @param index
   *          the index of the item to be selected or -1 to select none of the
   *          items.
   */
  public final void setSelected(final int index) {

    // handle the special case of setting the selected item to none.
    if (index == -1) {
      selectedIndex = index;
      button.setText(N_A);
      return;
    }

    assertIndex(getItemCount(), index);

    if (selectedIndex == index) {
      return;
    }

    if (!isEnabled(index)) {
      return;
    }

    selectedIndex = index;
    button.setText(getItemText(index));
  }

  /**
   * 
   * @return returns the currently selected item
   */
  public final int getSelected() {
    return selectedIndex;
  }

  @Override
  public void removeItem(final int index) {

    // make sure the operation is legal
    if (index < 0 || index >= getItemCount()) {
      return;
    }

    setSelected(-1);
    combo.removeItem(index);

    for (int i = 0; i < getItemCount(); i++) {
      if (isEnabled(i)) {
        setSelected(i);
        break;
      }
    }
  }

  @Override
  public int getTabIndex() {
    return button.getTabIndex();
  }

  @Override
  public void setAccessKey(final char key) {
    button.setAccessKey(key);
  }

  @Override
  public void setFocus(final boolean focused) {
    button.setFocus(focused);
  }

  @Override
  public void setTabIndex(final int index) {
    button.setTabIndex(index);
  }

  @Override
  public void upgrade() {
    combo.upgrade();
  }

  @Override
  public boolean isUpgraded() {
    return combo.isUpgraded();
  }

  /**
   * @param btn
   *          the button part of the dropdown
   */
  private void init(final Button btn) {
    button = btn;

    combo = new MenuCombo(button);
    combo.setEventSource(this);
    initWidget(combo);

    ItemClickListener clickListener;
    clickListener = new ItemClickListener() {

      @Override
      public void onItemClicked(final ItemClickEvent event) {

        setSelected(event.getIndex());
      }
    };
    addItemClickListener(clickListener);
  }

  /**
   * if no item is selected, then by default select the first enabled item in
   * the menu, otherwise don't do anything.
   * 
   * @param index
   *          the item to select
   */
  private void selectDefault(final int index) {
    // if no item is selected, then by default select the first enabled item in
    // the menu, otherwise don't do anything.
    if (selectedIndex != -1) {
      return;
    }

    // if the item is disabled it cannot be of selected
    if (!isEnabled(index)) {
      return;
    }

    selectedIndex = index;
    button.setText(getItemText(selectedIndex));
  }

  /**
   * The action button of the dropdown.
   */
  private Button button;

  /**
   * The menu combo part of the dropdown.
   */
  private MenuCombo combo;

  /**
   * holds the index of the selected item of the dropdown.
   */
  private int selectedIndex = -1;

  /** The string to display when there is no items to select. */
  private static final String N_A = "[N/A]";
}
