$('#inputRole').change(function(){

   selection = $('this').value();
   switch (selection) {
       case 'applicant':
           $('#otherType').hide();
           break;
       case 'manager':
           $('#otherType').show();
           break;
   }
});