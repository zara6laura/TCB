(function () {
    function createButton(label, disabled, onClick, active) {
        const button = document.createElement("button");
        button.type = "button";
        button.className = "btn btn-sm " + (active ? "btn-brand" : "btn-outline-secondary") + " table-page-btn";
        button.textContent = label;
        button.disabled = disabled;
        button.addEventListener("click", onClick);
        return button;
    }

    function initTablePagination(table) {
        if (!table || table.getAttribute("data-paginated") === "true") {
            return;
        }

        const tbody = table.tBodies && table.tBodies[0];
        if (!tbody) {
            return;
        }

        const rows = Array.from(tbody.rows);
        if (!rows.length) {
            return;
        }

        const perPage = 10;
        const totalRows = rows.length;
        const totalPages = Math.max(1, Math.ceil(totalRows / perPage));
        let currentPage = 1;

        const pager = document.createElement("div");
        pager.className = "table-paginator d-flex flex-wrap justify-content-between align-items-center gap-2 mt-2";

        const status = document.createElement("small");
        status.className = "text-muted fw-semibold";

        const controls = document.createElement("div");
        controls.className = "d-flex align-items-center gap-1";

        pager.appendChild(status);
        pager.appendChild(controls);

        const targetContainer = table.closest(".table-responsive") || table.parentElement;
        if (!targetContainer) {
            return;
        }
        targetContainer.insertAdjacentElement("afterend", pager);

        function renderRows() {
            const start = (currentPage - 1) * perPage;
            const end = Math.min(start + perPage, totalRows);

            rows.forEach(function (row, index) {
                row.classList.toggle("d-none", index < start || index >= end);
            });

            status.textContent = "Mostrando " + String(start + 1) + "-" + String(end) +
                " de " + String(totalRows) + " | Página " + String(currentPage) + "/" + String(totalPages);
        }

        function renderControls() {
            controls.innerHTML = "";

            controls.appendChild(
                createButton("«", currentPage === 1, function () {
                    if (currentPage > 1) {
                        currentPage -= 1;
                        renderRows();
                        renderControls();
                    }
                }, false)
            );

            const windowSize = 5;
            const half = Math.floor(windowSize / 2);
            let startPage = Math.max(1, currentPage - half);
            let endPage = Math.min(totalPages, startPage + windowSize - 1);
            startPage = Math.max(1, endPage - windowSize + 1);

            let page = startPage;
            while (page <= endPage) {
                (function (pageNumber) {
                    controls.appendChild(
                        createButton(String(pageNumber), false, function () {
                            currentPage = pageNumber;
                            renderRows();
                            renderControls();
                        }, pageNumber === currentPage)
                    );
                })(page);
                page += 1;
            }

            controls.appendChild(
                createButton("»", currentPage === totalPages, function () {
                    if (currentPage < totalPages) {
                        currentPage += 1;
                        renderRows();
                        renderControls();
                    }
                }, false)
            );
        }

        table.setAttribute("data-paginated", "true");
        renderRows();
        renderControls();
    }

    function initAllTables() {
        document.querySelectorAll("table").forEach(function (table) {
            initTablePagination(table);
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initAllTables);
    } else {
        initAllTables();
    }
})();
