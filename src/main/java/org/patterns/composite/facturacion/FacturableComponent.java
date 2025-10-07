package org.patterns.composite.facturacion;

import java.math.BigDecimal;
import java.util.List;

public interface FacturableComponent {
    BigDecimal total();
    default void add(FacturableComponent c) { throw new UnsupportedOperationException(); }
    default void remove(FacturableComponent c) { throw new UnsupportedOperationException(); }
    default List<FacturableComponent> hijos() { return List.of(); }
}
