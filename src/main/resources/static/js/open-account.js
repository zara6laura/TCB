
(function () {
    const menuItems = document.querySelectorAll(".open-account-menu-item[data-panel-target]");
    const panels = document.querySelectorAll(".menu-panel");
    const relationMainView = document.getElementById("relationMainView");
    const relationSavedView = document.getElementById("relationSavedView");
    const relationNewBtn = document.getElementById("relationNewBtn");
    const relationSaveBtn = document.getElementById("relationSaveBtn");
    const relationAddBtn = document.getElementById("relationAddBtn");
    const relationFormsContainer = document.getElementById("relationFormsContainer");
    const relIdOficial = document.getElementById("relIdOficial");
    const relNombrePersona = document.getElementById("relNombrePersona");
    const relProdVendible = document.getElementById("relProdVendible");
    const relEstadoSolicitud = document.getElementById("relEstadoSolicitud");
    const savedTipoRelacion = document.getElementById("savedTipoRelacion");
    const savedProdVendible = document.getElementById("savedProdVendible");
    const savedEstadoSolicitud = document.getElementById("savedEstadoSolicitud");
    const relationSavedBody = document.getElementById("relationSavedBody");
    const accountTariffDropdown = document.getElementById("accountTariffDropdown");
    const accountTariffToggle = document.getElementById("accountTariffToggle");
    const accountTariffMenu = document.getElementById("accountTariffMenu");
    const accountTariffSelectedText = document.getElementById("accountTariffSelectedText");
    const accountTariffOptions = document.querySelectorAll(".tariff-option");
    const accountTariffSelect = document.getElementById("accountTariffSelect");
    const selectedTipoCuentaLabel = document.getElementById("selectedTipoCuentaLabel");
    const selectedTipoCuentaLabe = document.getElementById("selectedTipoCuentaLabe");
    const accountNumAcInput = document.getElementById("accountNumAcInput");
    const activateAccountBtn = document.getElementById("activateAccountBtn");
    const accountStatusBadge = document.getElementById("accountStatusBadge");

    function extractTipoCuentaFromOption(optionValue) {
        if (!optionValue) {
            return "";
        }
        const parts = optionValue.split("|").map(function (part) {
            return part.trim();
        });
        return parts.length >= 5 ? parts[4] : "";
    }

    function extractTarifaCodeFromOption(optionValue) {
        if (!optionValue) {
            return "";
        }
        const parts = optionValue.split("|").map(function (part) {
            return part.trim();
        });
        return parts.length >= 4 ? parts[3] : "";
    }

    function syncSelectedTariff(optionValue) {
        const tipoCuenta = extractTipoCuentaFromOption(optionValue);
        const tarifaCode = extractTarifaCodeFromOption(optionValue);

        if (selectedTipoCuentaLabel && tipoCuenta) {
            selectedTipoCuentaLabel.textContent = tipoCuenta;
        }
        if (selectedTipoCuentaLabe && tipoCuenta) {
            selectedTipoCuentaLabe.textContent = tipoCuenta;
        }
        if (accountNumAcInput && tarifaCode) {
            accountNumAcInput.value = tarifaCode;
        }
    }

    function renderTariffOptions(selectedValue) {
        accountTariffOptions.forEach(function (option) {
            const value = option.getAttribute("data-value") || "";
            const plainText = (option.getAttribute("data-label") || option.textContent).replace(/^✓\s*/, "");
            option.setAttribute("data-label", plainText);
            const isSelected = value === selectedValue;
            option.classList.toggle("is-selected", isSelected);
            option.textContent = (isSelected ? "✓ " : "") + plainText;
        });
    }

    function closeTariffMenu() {
        if (!accountTariffMenu || !accountTariffToggle) {
            return;
        }
        accountTariffMenu.classList.add("d-none");
        accountTariffToggle.setAttribute("aria-expanded", "false");
    }

    function openTariffMenu() {
        if (!accountTariffMenu || !accountTariffToggle) {
            return;
        }
        accountTariffMenu.classList.remove("d-none");
        accountTariffToggle.setAttribute("aria-expanded", "true");
    }

    if (accountTariffSelect && accountTariffOptions.length) {
        const serverTipoCuenta = selectedTipoCuentaLabel ? selectedTipoCuentaLabel.textContent.trim().toUpperCase() : "";
        const serverTipoCuent = selectedTipoCuentaLabe ? selectedTipoCuentaLabe.textContent.trim().toUpperCase() : "";
        let selectedValue = accountTariffSelect.value;

        accountTariffOptions.forEach(function (option) {
            const optionValue = option.getAttribute("data-value") || "";
            const tipoCuentaOption = extractTipoCuentaFromOption(optionValue).toUpperCase();
            if (serverTipoCuenta && tipoCuentaOption === serverTipoCuenta) {
                selectedValue = optionValue;
            }
            if (serverTipoCuent && tipoCuentaOption === serverTipoCuent) {
                selectedValue = optionValue;
            }
        });

        if (!selectedValue) {
            const firstValue = accountTariffOptions[0].getAttribute("data-value");
            selectedValue = firstValue || "";
        }

        accountTariffSelect.value = selectedValue;
        if (accountTariffSelectedText) {
            accountTariffSelectedText.textContent = selectedValue.split("|").map(function (part) {
                return part.trim();
            }).join(" | ");
        }
        renderTariffOptions(selectedValue);
        syncSelectedTariff(selectedValue);

        accountTariffOptions.forEach(function (option) {
            option.addEventListener("click", function () {
                const value = option.getAttribute("data-value") || "";
                if (!value) {
                    return;
                }
                accountTariffSelect.value = value;
                if (accountTariffSelectedText) {
                    accountTariffSelectedText.textContent = value.split("|").map(function (part) {
                        return part.trim();
                    }).join(" | ");
                }
                renderTariffOptions(value);
                syncSelectedTariff(value);
                closeTariffMenu();
            });
        });

        if (accountTariffToggle) {
            accountTariffToggle.addEventListener("click", function () {
                if (accountTariffMenu && accountTariffMenu.classList.contains("d-none")) {
                    openTariffMenu();
                    return;
                }
                closeTariffMenu();
            });
        }

        document.addEventListener("click", function (event) {
            if (!accountTariffDropdown || accountTariffDropdown.contains(event.target)) {
                return;
            }
            closeTariffMenu();
        });
    }

    if (activateAccountBtn) {
        activateAccountBtn.addEventListener("click", function () {
            if (accountStatusBadge) {
                accountStatusBadge.textContent = "ACTIVADO";
            }
        });
    }

    function showRelationMain() {
        if (relationMainView) {
            relationMainView.classList.remove("d-none");
        }
        if (relationSavedView) {
            relationSavedView.classList.add("d-none");
        }
    }

    function showRelationSaved() {
        if (relationMainView) {
            relationMainView.classList.add("d-none");
        }
        if (relationSavedView) {
            relationSavedView.classList.remove("d-none");
        }
    }

    function getMainRelationTypeControl() {
        if (!relationFormsContainer) {
            return null;
        }
        return relationFormsContainer.querySelector("[data-rel-tipo]");
    }

    function normalizeRelationType(value) {
        return (value || "").trim().toUpperCase().replace(/\s+/g, " ");
    }

    function getSelectedRelationType(relationTypeControl) {
        if (!relationTypeControl) {
            return "";
        }
        const selectedOption = relationTypeControl.options[relationTypeControl.selectedIndex];
        const optionValue = selectedOption ? selectedOption.value : "";
        const optionText = selectedOption ? selectedOption.textContent : "";
        return normalizeRelationType(optionValue || optionText);
    }

    function toggleRelationTypeFields(formNode) {
        if (!formNode) {
            return;
        }
        const relationTypeControl = formNode.querySelector("[data-rel-tipo]");
        const garantiaWrap = formNode.querySelector("[data-rel-garantia-wrap]");
        const rznCambioWrap = formNode.querySelector("[data-rel-rzn-cambio-wrap]");
        const porcentajeWrap = formNode.querySelector("[data-rel-porcentaje-wrap]");
        const beneficiarioWrap = formNode.querySelector("[data-rel-beneficiario-wrap]");
        const parentescoWrap = formNode.querySelector("[data-rel-parentesco-wrap]");
        const contactoWrap = formNode.querySelector("[data-rel-contacto-wrap]");
        const selectedType = getSelectedRelationType(relationTypeControl);
        const isBeneficiario = selectedType === "BENEFICIARIO";
        const hideGarantia = selectedType === "TITULAR" || selectedType === "AUTORIZADO" || selectedType === "BENEFICIARIO";
        const hideRznCambio = isBeneficiario;
        const hidePorcentaje = selectedType === "TITULAR" || selectedType === "AUTORIZADO" || selectedType === "AVAL";

        if (garantiaWrap) {
            garantiaWrap.classList.toggle("d-none", hideGarantia);
        }
        if (rznCambioWrap) {
            rznCambioWrap.classList.toggle("d-none", hideRznCambio);
        }
        if (porcentajeWrap) {
            porcentajeWrap.classList.toggle("d-none", hidePorcentaje);
        }
        if (beneficiarioWrap) {
            beneficiarioWrap.classList.toggle("d-none", !isBeneficiario);
        }
        if (parentescoWrap) {
            parentescoWrap.classList.toggle("d-none", !isBeneficiario);
        }
        if (contactoWrap) {
            contactoWrap.classList.toggle("d-none", !isBeneficiario);
        }
    }

    function bindRelationTypeToggle(formNode) {
        if (!formNode) {
            return;
        }
        const relationTypeControl = formNode.querySelector("[data-rel-tipo]");
        if (!relationTypeControl) {
            return;
        }
        if (!relationTypeControl.hasAttribute("data-rel-toggle-bound")) {
            relationTypeControl.addEventListener("change", function () {
                toggleRelationTypeFields(formNode);
            });
            relationTypeControl.setAttribute("data-rel-toggle-bound", "1");
        }
        toggleRelationTypeFields(formNode);
    }

    function clearRelationFormValues(formNode) {
        if (!formNode) {
            return;
        }
        formNode.querySelectorAll("input").forEach(function (input) {
            input.value = "";
        });
        formNode.querySelectorAll("select").forEach(function (select) {
            select.selectedIndex = 0;
        });
        bindRelationTypeToggle(formNode);
    }

    if (relationFormsContainer) {
        relationFormsContainer.querySelectorAll(".relation-form-item").forEach(function (formNode) {
            bindRelationTypeToggle(formNode);
        });
    }

    if (relationNewBtn && relationFormsContainer) {
        relationNewBtn.addEventListener("click", function () {
            const baseForm = relationFormsContainer.querySelector(".relation-form-item");
            if (!baseForm) {
                return;
            }

            const newForm = baseForm.cloneNode(true);
            clearRelationFormValues(newForm);
            relationFormsContainer.appendChild(newForm);
        });
    }

    if (relationSaveBtn) {
        relationSaveBtn.addEventListener("click", function () {
            const relTipoRelacion = getMainRelationTypeControl();
            const tipoRel = relTipoRelacion && relTipoRelacion.value ? relTipoRelacion.value : "BENEFICIARIO";
            const idOficial = relIdOficial && relIdOficial.value ? relIdOficial.value : "E1662170";
            const nombre = relNombrePersona && relNombrePersona.value ? relNombrePersona.value : "JOSE MANUEL GARCIA GARCIA";
            const prod = relProdVendible && relProdVendible.value ? relProdVendible.value : "CUENTAAHORRO";
            const est = relEstadoSolicitud && relEstadoSolicitud.value ? relEstadoSolicitud.value : "SOLICITADO";

            if (savedTipoRelacion) {
                savedTipoRelacion.value = tipoRel;
            }
            if (savedProdVendible) {
                savedProdVendible.value = prod;
            }
            if (savedEstadoSolicitud) {
                savedEstadoSolicitud.value = est;
            }
            if (relationSavedBody) {
                // Build rows with textContent to avoid injecting untrusted HTML from form values.
                relationSavedBody.textContent = "";
                const rows = [
                    ["TITULAR", "1", "GRGRMN81120918", "GABRIEL MAURICIO PAZ SANTA ANA"],
                    [tipoRel, "1", idOficial, nombre]
                ];
                rows.forEach(function (cells) {
                    const tr = document.createElement("tr");
                    cells.forEach(function (cellValue) {
                        const td = document.createElement("td");
                        td.textContent = cellValue == null ? "" : String(cellValue);
                        tr.appendChild(td);
                    });
                    relationSavedBody.appendChild(tr);
                });
            }

            showRelationSaved();
        });
    }

    if (relationAddBtn) {
        relationAddBtn.addEventListener("click", function () {
            showRelationMain();
        });
    }

    if (window.TableRowSelection) {
        window.TableRowSelection.initTables("table.click-select-table");
    }

    if (!menuItems.length || !panels.length) {
        return;
    }

    menuItems.forEach(function (item) {
        item.addEventListener("click", function (event) {
            event.preventDefault();
            const target = item.getAttribute("data-panel-target");
            if (!target) {
                return;
            }

            menuItems.forEach(function (menu) {
                menu.classList.remove("active");
            });
            item.classList.add("active");

            panels.forEach(function (panel) {
                panel.classList.add("d-none");
            });

            const activePanel = document.getElementById(target);
            if (activePanel) {
                activePanel.classList.remove("d-none");
            }

            if (target === "panel-relacionar-personas") {
                showRelationMain();
            }
        });
    });
})();