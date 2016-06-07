/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

/**
 *
 * @author saroshmadara
 */
public class SymbolTables {
    private String name,type,scrope,accessModifier;

    public SymbolTables(String name, String type, String scrope, String accessModifier) {
        this.name = name;
        this.type = type;
        this.scrope = scrope;
        this.accessModifier = accessModifier;
    }

    public SymbolTables(String name, String type, String scrope) {
        this.name = name;
        this.type = type;
        this.scrope = scrope;
    }
    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScrope() {
        return scrope;
    }

    public void setScrope(String scrope) {
        this.scrope = scrope;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }
    
}
