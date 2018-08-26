package de.endrullis.idea.postfixtemplates.language;

import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupActionProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementAction;
import com.intellij.codeInsight.template.postfix.completion.PostfixTemplateLookupElement;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.util.Consumer;
import com.intellij.util.PlatformIcons;

/**
 * This {@link LookupActionProvider} allows you to jump directly to a postfix template definition.
 *
 * @author Stefan Endrullis &lt;stefan@endrullis.de&gt;
 */
public class CptLookupActionProvider implements LookupActionProvider {
	@Override
	public void fillActions(LookupElement element, final Lookup lookup, Consumer<LookupElementAction> consumer) {
		if (element instanceof PostfixTemplateLookupElement) {
			final PostfixTemplateLookupElement templateLookupElement = (PostfixTemplateLookupElement) element;
			final PostfixTemplate template = templateLookupElement.getPostfixTemplate();

			if (template instanceof Navigatable && ((Navigatable) template).canNavigate()) {
				consumer.consume(new LookupElementAction(PlatformIcons.EDIT, "Edit custom '" + template.getKey() + "' template") {
					@Override
					public Result performLookupAction() {
						final Project project = lookup.getProject();
						ApplicationManager.getApplication().invokeLater(() -> {
							if (project.isDisposed()) return;

							((Navigatable) template).navigate(true);
						});
						return Result.HIDE_LOOKUP;
					}
				});
			}
		}
	}
}
