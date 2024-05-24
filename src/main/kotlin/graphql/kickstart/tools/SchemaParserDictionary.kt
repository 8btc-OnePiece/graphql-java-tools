package graphql.kickstart.tools

import graphql.kickstart.tools.util.BiMap
import kotlin.reflect.KClass

class SchemaParserDictionary constructor(private val sourceClassPaths: List<String> = listOf("")){


    private val dictionary: BiMap<String, Class<*>> = BiMap.create()

    fun get(name: String): Class<*>? {
        return if (dictionary.containsKey(name)) {
            dictionary[name]
        } else {
            val clazz = findClassInSourceClassPaths(name);
            if (clazz != null) {
                add(clazz)
            }
            clazz
        }
    }

    private fun findClassInSourceClassPaths(className: String): Class<*>? {
        for (sourceClassPath in sourceClassPaths) {
            try {
                return Class.forName(sourceClassPath + className)
            } catch (ignore: ClassNotFoundException) { }
        }
        return null
    }


    fun getDictionary(): BiMap<String, Class<*>> = BiMap.unmodifiableBiMap(dictionary)

    /**
     * Add arbitrary classes to the parser's dictionary, overriding the generated type name.
     */
    fun add(name: String, clazz: Class<*>) = this.apply {
        this.dictionary.put(name, clazz)
    }

    /**
     * Add arbitrary classes to the parser's dictionary, overriding the generated type name.
     */
    fun add(name: String, clazz: KClass<*>) = this.apply {
        this.dictionary.put(name, clazz.java)
    }

    /**
     * Add arbitrary classes to the parser's dictionary, overriding the generated type name.
     */
    fun add(dictionary: Map<String, Class<*>>) = this.apply {
        this.dictionary.putAll(dictionary)
    }

    /**
     * Add arbitrary classes to the parser's dictionary.
     */
    fun add(clazz: Class<*>) = this.apply {
        this.add(clazz.simpleName, clazz)
    }

    /**
     * Add arbitrary classes to the parser's dictionary.
     */
    fun add(clazz: KClass<*>) = this.apply {
        this.add(clazz.java.simpleName, clazz)
    }

    /**
     * Add arbitrary classes to the parser's dictionary.
     */
    fun add(vararg dictionary: Class<*>) = this.apply {
        dictionary.forEach { this.add(it) }
    }

    /**
     * Add arbitrary classes to the parser's dictionary.
     */
    fun add(vararg dictionary: KClass<*>) = this.apply {
        dictionary.forEach { this.add(it) }
    }

    /**
     * Add arbitrary classes to the parser's dictionary.
     */
    fun add(dictionary: Collection<Class<*>>) = this.apply {
        dictionary.forEach { this.add(it) }
    }
}
