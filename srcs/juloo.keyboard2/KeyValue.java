package juloo.keyboard2;

import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import java.util.HashMap;

class KeyValue
{
  public static final int		EVENT_NONE = -1;
  public static final int		EVENT_CONFIG = -2;
  public static final int		EVENT_SWITCH_TEXT = -3;
  public static final int		EVENT_SWITCH_NUMERIC = -4;
  public static final int		EVENT_SWITCH_EMOJI = -5;
  public static final int		EVENT_SWITCH_BACK_EMOJI = -6;
  public static final int		EVENT_CHANGE_METHOD = -7;
  public static final char	CHAR_NONE = '\0';

  // Behavior flags
  public static final int FLAG_KEEP_ON = 1;
  public static final int FLAG_LOCK = (1 << 1);
  public static final int FLAG_NOREPEAT = (1 << 2);
  public static final int FLAG_NOCHAR = (1 << 3);
  public static final int FLAG_PRECISE_REPEAT = (1 << 4);
  public static final int FLAG_KEY_FONT = (1 << 5);

  // Internal flags
  public static final int FLAG_LOCKED = (1 << 8);

  // Modifier flags
  public static final int FLAG_CTRL = (1 << 10);
  public static final int FLAG_SHIFT = (1 << 11);
  public static final int FLAG_ALT = (1 << 12);
  public static final int FLAG_FN = (1 << 13);

  // Accent flags
  public static final int FLAG_ACCENT1 = (1 << 16); // Grave
  public static final int FLAG_ACCENT2 = (1 << 17); // Aigu
  public static final int FLAG_ACCENT3 = (1 << 18); // Circonflexe
  public static final int FLAG_ACCENT4 = (1 << 19); // Tilde
  public static final int FLAG_ACCENT5 = (1 << 20); // Cédille
  public static final int FLAG_ACCENT6 = (1 << 21); // Tréma

  public static final int FLAGS_ACCENTS = FLAG_ACCENT1 | FLAG_ACCENT2 |
    FLAG_ACCENT3 | FLAG_ACCENT4 | FLAG_ACCENT5 | FLAG_ACCENT6;

  public final String name;
  public final String symbol;
  public final char char_;
  public final int eventCode;
  public final int flags;

  public KeyValue withCharAndSymbol(String s, char c)
  {
    return new KeyValue(name, s, c, eventCode, flags);
  }

  private static HashMap<String, KeyValue> keys = new HashMap<String, KeyValue>();

  public KeyValue(String n, String s, char c, int e, int f)
  {
    name = n;
    symbol = s;
    char_ = c;
    eventCode = e;
    flags = f;
  }

  public static KeyValue	getKeyByName(String name)
  {
    return (KeyValue.keys.get(name));
  }

  private static void addKey(String name, String symbol, char c, int event, int flags)
  {
    keys.put(name, new KeyValue(name, symbol, c, event, flags));
  }

  private static void addCharKey(char c, int event)
  {
    String name = String.valueOf(c);
    addKey(name, name, c, event, 0);
  }

  private static void addModifierKey(String name, String symbol, int extra_flags)
  {
    addKey(name, symbol, CHAR_NONE, EVENT_NONE,
        FLAG_KEEP_ON | FLAG_NOCHAR | FLAG_NOREPEAT | extra_flags);
  }

  private static void addSpecialKey(String name, String symbol, int event)
  {
    addKey(name, symbol, CHAR_NONE, event, FLAG_NOREPEAT);
  }

  private static void addEventKey(String name, String symbol, int event)
  {
    addEventKey(name, symbol, event, 0);
  }

  private static void addEventKey(String name, String symbol, int event, int flags)
  {
    addKey(name, symbol, CHAR_NONE, event, flags);
  }

  static
  {
    String chars = "<>&\"'(-_)=°+"
      + "~#{[|`\\^@]}"
      + "^$*,;:!£%µ?./§";
    for (int i = 0; i < chars.length(); i++)
      addCharKey(chars.charAt(i), EVENT_NONE);

    addModifierKey("shift", "⇧", FLAG_LOCK | FLAG_SHIFT);
    addModifierKey("ctrl", "Ctrl", FLAG_CTRL);
    addModifierKey("alt", "Alt", FLAG_ALT);
    addModifierKey("accent1", "\u02CB", FLAG_ACCENT1);
    addModifierKey("accent2", "\u00B4", FLAG_ACCENT2);
    addModifierKey("accent3", "\u02C6", FLAG_ACCENT3);
    addModifierKey("accent4", "\u02DC", FLAG_ACCENT4);
    addModifierKey("accent5", "\u00B8", FLAG_ACCENT5);
    addModifierKey("accent6", "\u00A8", FLAG_ACCENT6);
    addModifierKey("fn", "Fn", FLAG_FN);

    addCharKey('a', KeyEvent.KEYCODE_A);
    addCharKey('b', KeyEvent.KEYCODE_B);
    addCharKey('c', KeyEvent.KEYCODE_C);
    addCharKey('d', KeyEvent.KEYCODE_D);
    addCharKey('e', KeyEvent.KEYCODE_E);
    addCharKey('f', KeyEvent.KEYCODE_F);
    addCharKey('g', KeyEvent.KEYCODE_G);
    addCharKey('h', KeyEvent.KEYCODE_H);
    addCharKey('i', KeyEvent.KEYCODE_I);
    addCharKey('j', KeyEvent.KEYCODE_J);
    addCharKey('k', KeyEvent.KEYCODE_K);
    addCharKey('l', KeyEvent.KEYCODE_L);
    addCharKey('m', KeyEvent.KEYCODE_M);
    addCharKey('n', KeyEvent.KEYCODE_N);
    addCharKey('o', KeyEvent.KEYCODE_O);
    addCharKey('p', KeyEvent.KEYCODE_P);
    addCharKey('q', KeyEvent.KEYCODE_Q);
    addCharKey('r', KeyEvent.KEYCODE_R);
    addCharKey('s', KeyEvent.KEYCODE_S);
    addCharKey('t', KeyEvent.KEYCODE_T);
    addCharKey('u', KeyEvent.KEYCODE_U);
    addCharKey('v', KeyEvent.KEYCODE_V);
    addCharKey('w', KeyEvent.KEYCODE_W);
    addCharKey('x', KeyEvent.KEYCODE_X);
    addCharKey('y', KeyEvent.KEYCODE_Y);
    addCharKey('z', KeyEvent.KEYCODE_Z);
    addCharKey('0', KeyEvent.KEYCODE_0);
    addCharKey('1', KeyEvent.KEYCODE_1);
    addCharKey('2', KeyEvent.KEYCODE_2);
    addCharKey('3', KeyEvent.KEYCODE_3);
    addCharKey('4', KeyEvent.KEYCODE_4);
    addCharKey('5', KeyEvent.KEYCODE_5);
    addCharKey('6', KeyEvent.KEYCODE_6);
    addCharKey('7', KeyEvent.KEYCODE_7);
    addCharKey('8', KeyEvent.KEYCODE_8);
    addCharKey('9', KeyEvent.KEYCODE_9);

    addSpecialKey("config", "Conf", EVENT_CONFIG);
    addSpecialKey("switch_text", "ABC", EVENT_SWITCH_TEXT);
    addSpecialKey("switch_numeric", "123+", EVENT_SWITCH_NUMERIC);
    addSpecialKey("switch_emoji", ":)", EVENT_SWITCH_EMOJI);
    addSpecialKey("switch_back_emoji", "ABC", EVENT_SWITCH_BACK_EMOJI);
    addSpecialKey("change_method", "⊞", EVENT_CHANGE_METHOD);

    addEventKey("esc", "Esc", KeyEvent.KEYCODE_ESCAPE);
    addEventKey("enter", "\uE800", KeyEvent.KEYCODE_ENTER, FLAG_KEY_FONT);
    addEventKey("up", "\uE80B", KeyEvent.KEYCODE_DPAD_UP, FLAG_KEY_FONT | FLAG_PRECISE_REPEAT);
    addEventKey("right", "\uE80C", KeyEvent.KEYCODE_DPAD_RIGHT, FLAG_KEY_FONT | FLAG_PRECISE_REPEAT);
    addEventKey("down", "\uE809", KeyEvent.KEYCODE_DPAD_DOWN, FLAG_KEY_FONT | FLAG_PRECISE_REPEAT);
    addEventKey("left", "\uE80A", KeyEvent.KEYCODE_DPAD_LEFT, FLAG_KEY_FONT | FLAG_PRECISE_REPEAT);
    addEventKey("page_up", "⇞", KeyEvent.KEYCODE_PAGE_DOWN);
    addEventKey("page_down", "⇟", KeyEvent.KEYCODE_PAGE_UP);
    addEventKey("home", "↖", KeyEvent.KEYCODE_HOME);
    addEventKey("end", "↗", KeyEvent.KEYCODE_MOVE_END);
    addEventKey("backspace", "⌫", KeyEvent.KEYCODE_DEL, FLAG_PRECISE_REPEAT);
    addEventKey("delete", "⌦", KeyEvent.KEYCODE_FORWARD_DEL, FLAG_PRECISE_REPEAT);
    addEventKey("insert", "Ins", KeyEvent.KEYCODE_INSERT);
    addEventKey("f1", "F1", KeyEvent.KEYCODE_F1);
    addEventKey("f2", "F2", KeyEvent.KEYCODE_F2);
    addEventKey("f3", "F3", KeyEvent.KEYCODE_F3);
    addEventKey("f4", "F4", KeyEvent.KEYCODE_F4);
    addEventKey("f5", "F5", KeyEvent.KEYCODE_F5);
    addEventKey("f6", "F6", KeyEvent.KEYCODE_F6);
    addEventKey("f7", "F7", KeyEvent.KEYCODE_F7);
    addEventKey("f8", "F8", KeyEvent.KEYCODE_F8);
    addEventKey("f9", "F9", KeyEvent.KEYCODE_F9);
    addEventKey("f10", "F10", KeyEvent.KEYCODE_F10);

    addKey("tab",		"↹",		'\t',		KeyEvent.KEYCODE_TAB,			0);
    addKey("space",	" ",		' ',		KeyEvent.KEYCODE_SPACE,			0);
  }
}
