package com.icodening.easyconfig.command;

import com.icodening.easyconfig.util.GlobalThrowableStore;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author icodening
 * @date 2023.11.03
 */
public class StacktraceCommand implements EmptyArgumentsCommand {

    @Override
    public String getName() {
        return "stacktrace";
    }

    @Override
    public AttributedString handle(CommandContext context, List<String> args) {
        Throwable throwable = GlobalThrowableStore.getThrowable();
        if (throwable == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
        throwable.printStackTrace(new PrintStream(bos));
        return new AttributedString(bos.toString(), new AttributedStyle().foreground(AttributedStyle.RED));
    }
}
