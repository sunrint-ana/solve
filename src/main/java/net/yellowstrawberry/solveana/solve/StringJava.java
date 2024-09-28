package net.yellowstrawberry.solveana.solve;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

import static java.util.Objects.requireNonNull;

public class StringJava extends SimpleJavaFileObject {

    private String sourceCode;

    public StringJava(String name, String sourceCode) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.sourceCode = requireNonNull(sourceCode, "Got null parameter `sourceCode`");
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}