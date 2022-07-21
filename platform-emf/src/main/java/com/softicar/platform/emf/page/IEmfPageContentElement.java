package com.softicar.platform.emf.page;

import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Optional;

public interface IEmfPageContentElement extends IDomParentElement {

	Optional<IEmfPage<?>> getPage();
}
