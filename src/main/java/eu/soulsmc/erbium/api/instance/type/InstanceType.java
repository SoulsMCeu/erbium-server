package eu.soulsmc.erbium.api.instance.type;

import eu.soulsmc.erbium.api.instance.generator.FlatGenerator;
import eu.soulsmc.erbium.api.instance.generator.VoidGenerator;
import net.minestom.server.instance.generator.Generator;

public enum InstanceType {

    FLAT(new FlatGenerator()),
    VOID(new VoidGenerator());


    private final Generator generator;

    InstanceType(Generator generator) {
        this.generator = generator;
    }

    public Generator generator() {
        return this.generator;
    }
}
