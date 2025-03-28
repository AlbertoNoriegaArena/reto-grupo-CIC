import { MatPaginatorIntl } from '@angular/material/paginator';

export function CustomPaginator() {
    const customPaginatorIntl = new MatPaginatorIntl();

    customPaginatorIntl.itemsPerPageLabel = 'Items por página:';
    customPaginatorIntl.nextPageLabel = 'Siguiente';
    customPaginatorIntl.previousPageLabel = 'Anterior';
    customPaginatorIntl.firstPageLabel = 'Primera página';
    customPaginatorIntl.lastPageLabel = 'Última página';

    // Traducción de "1 – 5 of 13" a "1 – 5 de 13"
    customPaginatorIntl.getRangeLabel = (page: number, pageSize: number, length: number) => {
        if (length === 0 || pageSize === 0) {
            return `0 de ${length}`;
        }
        const startIndex = page * pageSize;
        const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
        return `${startIndex + 1} – ${endIndex} de ${length}`;
    };

    return customPaginatorIntl;
}
