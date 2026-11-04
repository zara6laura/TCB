(function () {
    function paintRow(row, selected) {
        row.querySelectorAll("td").forEach(function (cell, cellIndex) {
            if (!selected) {
                cell.style.removeProperty("background-color");
                cell.style.removeProperty("color");
                cell.style.removeProperty("font-weight");
                cell.style.removeProperty("box-shadow");
                cell.style.removeProperty("padding-left");
                return;
            }

            cell.style.setProperty("background-color", "#b4860e", "important");
            cell.style.setProperty("color", "#ffffff", "important");
            cell.style.setProperty("font-weight", "700", "important");
            cell.style.setProperty("box-shadow", "inset 0 0 0 9999px #b4860e", "important");

            if (cellIndex === 0) {
                cell.style.setProperty("padding-left", "1.5rem", "important");
            }
        });
    }

    function selectRow(table, row, options) {
        const settings = options || {};
        const rows = table.__selectRows || [];

        rows.forEach(function (item) {
            item.classList.remove("selected-row");
            item.setAttribute("aria-selected", "false");
            paintRow(item, false);
        });

        row.classList.add("selected-row");
        row.setAttribute("aria-selected", "true");
        paintRow(row, true);

        if (settings.flashOnSelect) {
            row.classList.add("click-flash");
            setTimeout(function () {
                row.classList.remove("click-flash");
            }, 180);
        }

        if (typeof settings.onSelect === "function") {
            settings.onSelect(row, table);
        }
    }

    function initTable(table, options) {
        const settings = options || {};
        const rowSelector = settings.rowSelector || "tbody tr";

        if (!table || table.getAttribute("data-click-select-initialized") === "true") {
            return;
        }

        const rows = Array.from(table.querySelectorAll(rowSelector));
        if (!rows.length) {
            return;
        }

        table.__selectRows = rows;
        table.setAttribute("data-click-select-initialized", "true");

        table.addEventListener("click", function (event) {
            const targetRow = event.target.closest(rowSelector);
            if (!targetRow || !table.contains(targetRow)) {
                return;
            }
            selectRow(table, targetRow, settings);
        });

        if (typeof settings.onDoubleClick === "function") {
            table.addEventListener("dblclick", function (event) {
                const targetRow = event.target.closest(rowSelector);
                if (!targetRow || !table.contains(targetRow)) {
                    return;
                }
                selectRow(table, targetRow, settings);
                settings.onDoubleClick(targetRow, table);
            });
        }

        if (settings.initialSelectFirst) {
            selectRow(table, rows[0], settings);
        }
    }

    function initTables(selector, options) {
        const finalSelector = selector || "table.click-select-table";
        document.querySelectorAll(finalSelector).forEach(function (table) {
            initTable(table, options);
        });
    }

    window.TableRowSelection = {
        initTable: initTable,
        initTables: initTables,
        selectRow: selectRow
    };

    function autoInit() {
        document.querySelectorAll("table.click-select-table").forEach(function (table) {
            if (table.getAttribute("data-click-select-auto") === "false") {
                return;
            }
            initTable(table);
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", autoInit);
    } else {
        autoInit();
    }
})();
