package gpg.jfxjpa.application.language;

import static gpg.jfxjpa.application.util.ReflectionUtils.getUnsafeFieldValue;
import static java.util.ResourceBundle.getBundle;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.resources.ControlResources;

import gpg.jfxjpa.application.util.PropertyLoader;

/**
 * The class with all messages of this application.
 */
public abstract class Messages {

    private static ResourceBundle BUNDLE;

    private static final String FIELD_NAME = "lookup";
    private static final String BUNDLE_NAME = "messages/messages";
    private static final String CONTROLS_BUNDLE_NAME = "com/sun/javafx/scene/control/skin/resources/controls";


    static {
        final Locale locale = Locale.getDefault();
        final ClassLoader classLoader = ControlResources.class.getClassLoader();

        final ResourceBundle controlBundle = getBundle(CONTROLS_BUNDLE_NAME,  locale, classLoader, PropertyLoader.getInstance());

        final ResourceBundle overrideBundle = getBundle(CONTROLS_BUNDLE_NAME,  PropertyLoader.getInstance());

        final Map override = getUnsafeFieldValue(overrideBundle, FIELD_NAME);
        final Map original = getUnsafeFieldValue(controlBundle, FIELD_NAME);

        //noinspection ConstantConditions,ConstantConditions,unchecked
        original.putAll(override);

        BUNDLE = getBundle(BUNDLE_NAME, PropertyLoader.getInstance());

    }

    public static ResourceBundle GetBundle() {
        return BUNDLE;
    }
}