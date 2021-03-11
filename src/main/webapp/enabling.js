<!-- Script for input enabling\disabling-->

function EnableDisable() {
    //Reference the file.
    var countrySelect = document.getElementById("countrySelect");
    var auto = document.getElementById("auto");
    var local = document.getElementById("local");

    //Verify checkboxes.
    countrySelect.disabled = auto.checked || local.checked;
    auto.disabled =  local.checked;
    local.disabled =  auto.checked;
}