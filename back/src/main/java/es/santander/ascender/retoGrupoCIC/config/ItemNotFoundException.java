package es.santander.ascender.retoGrupoCIC.config;

public class ItemNotFoundException extends RuntimeException {

        private Long itemId;

        public ItemNotFoundException(){
            
        }
    
        public ItemNotFoundException(Long itemId) {
            super("No se encontró el ítem con ID: " + itemId);
            this.itemId = itemId;
        }
    
        public Long getItemId() {
            return itemId;
        }
    }
