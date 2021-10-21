$('input[type="checkbox"]').change(function(){
    this.value = (Number(this.checked));
});