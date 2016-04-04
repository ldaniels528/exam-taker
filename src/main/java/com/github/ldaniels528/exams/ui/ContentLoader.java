package com.github.ldaniels528.exams.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

/**
 * Content Loader
 *
 * @author lawrence.daniels@gmail.com
 */
class ContentLoader {
    private static final String LOCAL_PATH = "./resources%s";
    private static final ContentLoader instance = new ContentLoader();

    /**
     * Default Constructor
     */
    private ContentLoader() {
        super();
    }

    /**
     * Returns the singleton instance of the content loader
     *
     * @return the singleton instance
     */
    static ContentLoader getInstance() {
        return instance;
    }

    /**
     * Retrieves the icon from the given resource path
     *
     * @param resourcePath the given resource path
     * @return the requested {@link Image image icon} or <tt>null</tt> if not found
     */
    ImageIcon loadIcon(final String resourcePath) {
        return new ImageIcon(getResource(resourcePath));
    }

    /**
     * Retrieves the icon from the given resource path
     *
     * @param resourcePath the given resource path
     * @return the requested {@link Image image icon} or <tt>null</tt> if not found
     */
    public Image loadImage(final String resourcePath) {
        return new ImageIcon(getResource(resourcePath)).getImage();
    }

    /**
     * Retrieves the URL for the resource specified by the given resource path
     *
     * @param resourcePath the given resource path
     * @return the requested {@link URL resource}
     * @throws ContentLoadException
     */
    protected URL getResource(final String resourcePath)
            throws ContentLoadException {
        // first, try to load the resource from the .jar file
        final URL url = getClass().getResource(resourcePath);
        if (url != null) {
            return url;
        }

        // if not found, look for the file locally
        else {
            final File localFile = new File(format("resources%s", resourcePath));
            if (localFile.exists()) {
                try {
                    return new URL(format("file://%s", localFile.getAbsolutePath()));
                } catch (final MalformedURLException e) {
                    throw new ContentLoadException(resourcePath, e);
                }
            } else {
                throw new ContentLoadException(resourcePath);
            }
        }
    }

    /**
     * Retrieves the input stream for the resource specified by the given resource path
     *
     * @param resourcePath the given resource path
     * @return the requested {@link InputStream stream}
     * @throws ContentLoadException
     */
    protected InputStream getResourceAsStream(final String resourcePath)
            throws ContentLoadException {
        try {
            // first, try to load the resource from the .jar file
            final URL url = getClass().getResource(resourcePath);
            if (url != null) {
                return url.openStream();
            }

            // if not found, look for the file locally
            else {
                final File localFile = new File(format(LOCAL_PATH, resourcePath));
                return new FileInputStream(localFile);
            }
        } catch (final IOException e) {
            throw new ContentLoadException(resourcePath, e);
        }
    }
}
