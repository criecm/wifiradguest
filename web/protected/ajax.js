var shortdeb ;
var purl = "/Arrubaguest/protected/index.jsp";
var o = new oevnt();
var pdfile;
var tab_radadmin = new Array();
 
 
 
function oevnt (nb,n,fa,id) {
        this.nombre = nb ;
        this.nom = n;
        this.firstacct = fa;
        this.id = id;
}

function obj_radadmin(e,c,fa,fl,inf,nba,exp) {
    this.evenement = e,
    this.createur = c,
    this.firstaccount = fa,
    this.firstlogin = fl,
    this.infos = inf,
    this.nbaccount = nba,
    this.expiration= exp
}

  function print(url)
  {
      var _this = this,
          iframeId = 'iframeprint',
          $iframe = $('iframe#iframeprint');
   
      $("iframe#iframeprint").load(url, function(responseText, textStatus, XMLHttpRequest) {
          
         console.log(responseText);
         
         $(this).contents().find('body').append(responseText);
         
          console.log("COmplete download LOULOU");
       
          _this.callPrint(iframeId);
      });
  }

  //initiates print once content has been loaded into iframe
  function callPrint(iframeId) {
      var PDF = document.getElementById(iframeId);
      PDF.focus();
      PDF.contentWindow.print() ;
  }

/*
function printTrigger(elementId) {
   
    var getMyFrame = document.getElementById(elementId);
    getMyFrame.focus();
    getMyFrame.contentWindow.print();
}

function printPdf(url) {
  
   var iframe = this.x;
   
  if (!this.x) {
     
    iframe = this.x = document.createElement('iframe');
    iframe.setAttribute('id','idprint');
    
    document.body.appendChild(iframe);

    iframe.style.display = 'none';
  
    $("iframe#idprint").load(url, function() {
        console.log("COmplete download LOULOU");
        iframe.focus();
        iframe.contentWindow.print();
    });
    
   iframe.onload = function() {
        console.log('ALLO');
      setTimeout(function() {
        iframe.focus();
        iframe.contentWindow.print();
      }, 1);
    };
  

  iframe.src = url;  
}
this.x=null;
}
*/

function Logout() {
        var myobj = 'formid=' + 5;
         
          $(function() {
            $.ajax({
                url : '/Arrubaguest/Create_Accounts',
                type : 'POST',
                traditionnal : 'true',
                data : myobj ,
                dataType : 'html',
                success : function(data){       
                    $(location).attr('href',"https://auth.univ.fr/cas/logout");
                },
                error:function (xhr, ajaxOptions, thrownError){
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    $(location).attr('href',purl);
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
              }
            });
        });
    } 
    
function ClearAllPanel() {
    $(".panel").hide();
    $("#tabev").hide();
    $(".jumbotron").hide();
    $("footer").hide();
    $("#idtext").hide();
}    

function init_display() {
    $(".panel").hide();
    $("#tabev").hide();
    $(".jumbotron").show();
    $("#idtext").show();
    $("footer").show();
}

function init() {
    
 var l = Ladda.create(document.querySelector('button#sendevnt'));
 var m = Ladda.create(document.querySelector('button#sendprov'));
 var k = Ladda.create(document.querySelector('button#sendadd'));
 var p = Ladda.create(document.querySelector('button#sendpers'));
  
    $("#arrubaform").on('submit', function( event ) {
         
            var formid = 'cevt';
            var evnt = $("#evenement");
            var nbre = $("#nbaccount");
            var datedebut = $("#datedeb");    
            var duree = $("#comboduree");
            var evntlieu = $("#evntlieu");
            
            var bValid = true;
            var checkerror = true;
            
           var allFields = $( [] ).add(evnt).add(nbre).add(datedebut).add(duree).add(evntlieu),
           tips = $( ".validateTips" ); 

      function updateTips( t ) {
                tips
                .text( t )
                .addClass( "ui-state-highlight" );
                setTimeout(function() {
                    tips.removeClass( "ui-state-highlight", 1500 );
                }, 800 );
            }
      
             function checkLength(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips("La longueur de " + n + " doit être entre " +
                        min + " et " + max + ".");
                return false;
            } else {
                //     console.log("XXXXXX : " + o.val().replace(/"/g,"\\\""));
                o.val(o.val().replace(/\\/g, "\\\\"))
                o.val(o.val().replace(/"/g, "\\\""));
                return true;
            }
        }          
      
            function checkExist(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips(n + " est un champ obligtatoire.");
                return false;
            } else {
                return true;
            }
        }     
      
            function checkRegexp( o, regexp, n ) {
                if ( !( regexp.test( o.val() ) ) ) {
                    o.addClass( "ui-state-error" );
                    updateTips( n );
                    return false;
                } else {
                    return true;
                }
            }
            console.log(evnt);
            console.log(nbre);
            
            event.preventDefault();
           
            allFields.removeClass( "ui-state-error" );  
            
            bValid = bValid && checkLength( evnt, "Evenement", 6, 100 );
            bValid = bValid && checkRegexp( evnt, /^[a-zA-Z0-9\u00C0-\u00FF_\.'\(\)\s]+$/ ,"Caractères alphanumériques et accentués et '()._ " );
            
            bValid = bValid && checkRegexp( nbre, /^[1-9][0-9]{0,2}$/ ,"Nombre compris entre 1 et 999 " );
      
            bValid = bValid && checkExist( datedebut, "Date début", 6, 18 );
            bValid = bValid && checkExist( duree, "Durée", 1, 1 );
            
            bValid = bValid && checkRegexp( evntlieu, /^[a-zA-Z0-9\u00C0-\u00FF_\.'\(\)\s]+$/ ,"Caractères alphanumériques et accentués et '()._ " );
            bValid = bValid && checkLength( evntlieu, "Lieu événement", 4, 150 );
            
            console.log (bValid);
            
            if ( bValid === true) {
                
            tips.text('');    
                
             var mydata = "evenement=" + encodeURIComponent(evnt.val()) + "&nbaccount=" + encodeURIComponent(nbre.val())
                      + "&datedeb=" + encodeURIComponent(datedebut.val()) + "&duree=" + encodeURIComponent(duree.val())
                      + "&formid=" + encodeURIComponent(formid) + "&shortdeb=" + encodeURIComponent(shortdeb)
                      + "&evntlieu=" + encodeURIComponent(evntlieu.val());
                      
            $.ajax({
                url: '/Arrubaguest/Create_Accounts',
                type : 'POST',
                traditionnal : 'true',
                data : mydata,
                beforeSend: function(xmlHttpRequest) { 
	 	     l.start();
                },
                complete: function() {
                      l.stop();
                },
               success : function(data){
                   if ( data === 'erreur database') {
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error").modal('show');
                   } else if (data === 'erreur PDF'){
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error .modal-body").append("<p><b>Erreur céation PDF</b>&nbsp;<span class=\"glyphicon glyphicon-warning-sign\"></span></p>");
                       $("#modal_error").modal('show');
                   } else {
                     $("#accok").modal({backdrop:false});
                     $("#accok").modal('show');
                     console.log("nom du fichier " + data);
                     pdfile = data;
                 }
                },
               error:function (xhr, ajaxOptions, thrownError){
                   l.stop();
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    $(location).attr('href',"/Arrubaguest/index.jsp");
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
            }
            });
            
            $("#arrubaform")[0].reset();
            
             }
         });
         
     $("#arrubaform0").on('submit', function( event ) {
            
            var formid = 'cprov';
            var nbre = $("#nbacct");
            var duree = $("#combo");
            var respon = $("#idrespon");
            
            var bValid = true;
            var checkerror = true;
            
           var allFields = $( [] ).add(nbre).add(duree).add(respon),
           tips = $( ".validateTips0" ); 

      function updateTips( t ) {
                tips
                .text( t )
                .addClass( "ui-state-highlight" );
                setTimeout(function() {
                    tips.removeClass( "ui-state-highlight", 1500 );
                }, 2000 );
            }
      
             function checkLength(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips("La longueur de " + n + " doit être entre " +
                        min + " et " + max + ".");
                return false;
            } else {
                //     console.log("XXXXXX : " + o.val().replace(/"/g,"\\\""));
                o.val(o.val().replace(/\\/g, "\\\\"))
                o.val(o.val().replace(/"/g, "\\\""));
                return true;
            }
        }          
      
            function checkExist(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips(n + " est un champ obligtatoire.");
                return false;
            } else {
                return true;
            }
        }     
      
            function checkRegexp( o, regexp, n ) {
                if ( !( regexp.test( o.val() ) ) ) {
                    o.addClass( "ui-state-error" );
                    updateTips( n );
                    return false;
                } else {
                    return true;
                }
            }
            
            console.log(nbre);
            console.log(duree);
            
            event.preventDefault();
                   
            allFields.removeClass( "ui-state-error" );  
            
            bValid = bValid && checkRegexp( nbre, /^[1-9][0-9]{0,2}$/ ,"Nombre compris entre 1 et 999 " );
            bValid = bValid && checkExist( duree, "Durée", 1, 1 );
            bValid = bValid && checkLength(respon,"Responsable",5, 50);
            
            console.log (bValid + " " + respon.val());
            
            if ( bValid === true) {
                
            tips.text('');    
          
            var mydata = "formid=" + encodeURIComponent(formid) + 
                         "&duree=" + encodeURIComponent(duree.val()) + 
                         "&nbacct=" + encodeURIComponent(nbre.val()) +
                         "&respon=" + encodeURIComponent(respon.val());
            
            $.ajax({
                url:"/Arrubaguest/Create_Accounts",
                type : 'POST',
                traditionnal : 'true',
                data : mydata,
                beforeSend: function(xmlHttpRequest) { 
	 	     m.start();
                },
                complete: function() {
                      m.stop();
                }, 
               success : function(data){
                    console.log(data + " MOI");
                    if ( data === 'erreur database') {
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error").modal('show');
                   } else if (data === 'erreur PDF'){
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error .modal-body").append("<p><b>Erreur céation PDF</b>&nbsp;<span class=\"glyphicon glyphicon-warning-sign\"></span></p>");
                       $("#modal_error").modal('show');
                   } else {    
                    $("#accok").modal({backdrop:false});
                    $("#accok").modal('show');
                     pdfile = data;
                 }
                },
               error:function (xhr, ajaxOptions, thrownError){
                   m.stop();
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    $(location).attr('href',"/Arrubaguest/index.jsp");
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
            }
            });
        } 
            $("#arrubaform0")[0].reset();
            
         });
         
         $("#arrubaform1").on('submit', function( event ) {
            
            var formid = 'cpadd';
            var n = $("#nbadd");
            var ids = $("#searchevnt");
            
            console.log(n);
           
            event.preventDefault();
            
            var bValid = true;
            var checkerror = true;
            
           var allFields = $( [] ).add(n).add(ids),
           tips = $( ".validateTips1" ); 

      function updateTips( t ) {
                tips
                .text( t )
                .addClass( "ui-state-highlight" );
                setTimeout(function() {
                    tips.removeClass( "ui-state-highlight", 1500 );
                }, 800 );
            }
      
             function checkLength(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips("La longueur de " + n + " doit être entre " +
                        min + " et " + max + ".");
                return false;
            } else {
                //     console.log("XXXXXX : " + o.val().replace(/"/g,"\\\""));
                o.val(o.val().replace(/\\/g, "\\\\"))
                o.val(o.val().replace(/"/g, "\\\""));
                return true;
            }
        }          
      
      function checkExist(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips(n + " est un champ obligtatoire.");
                return false;
            } else {
                return true;
            }
        }     
      
            function checkRegexp( o, regexp, n ) {
                if ( !( regexp.test( o.val() ) ) ) {
                    o.addClass( "ui-state-error" );
                    updateTips( n );
                    return false;
                } else {
                    return true;
                }
            }
            
            allFields.removeClass( "ui-state-error" );  
            
            bValid = bValid && checkExist( ids, "Recherche èvènement", 1, 100 );
            bValid = bValid && checkRegexp( n, /^[1-9][0-9]{0,2}$/ ,"Nombre compris entre 1 et 999 " );
            
             console.log (bValid);
            
            if ( bValid === true) {
                
            tips.text('');       
            
            var mydata = "formid=" + encodeURIComponent(formid) + 
                        "&nom=" + encodeURIComponent(o.nom) + 
                        "&id=" + encodeURIComponent(o.id)  + 
                        "&nbadd=" + encodeURIComponent(n.val())  +
                        "&oldnb=" + encodeURIComponent(o.nombre) +
                        "&firstacct=" + encodeURIComponent(o.firstacct);
            $.ajax({
                url:"/Arrubaguest/Create_Accounts",
                type : 'POST',
                traditionnal : 'true',
                data : mydata,
                beforeSend: function(xmlHttpRequest) { 
	 	     k.start();
                },
                complete: function() {
                      k.stop();
                }, 
                success : function(data){
                    console.log(data + " MOI");
                    if ( data === 'erreur database') {
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error").modal('show');
                   } else if (data === 'erreur PDF'){
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error .modal-body").append("<p><b>Erreur céation PDF</b>&nbsp;<span class=\"glyphicon glyphicon-warning-sign\"></span></p>");
                       $("#modal_error").modal('show');
                   } else {
                    $("#accok").modal({backdrop:false});
                    $("#accok").modal('show');
                    pdfile = data;
                }
                },
                error:function (xhr, ajaxOptions, thrownError){
                    k.stop();
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    $(location).attr('href',"/Arrubaguest/index.jsp");
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
            }
            });
        }  
            $("#arrubaform1")[0].reset();
         
         });
         
           $("#arrubaform3").on('submit', function( event ) {
            
            var formid = 'cpers';
            var n = 1;
            var combopers = $("#combopers");
            
            event.preventDefault();
            
            var bValid = true;
            var checkerror = true;
            
           var allFields = $( [] ).add(combopers),
           tips = $( ".validateTips3" ); 

      function updateTips( t ) {
                tips
                .text( t )
                .addClass( "ui-state-highlight" );
                setTimeout(function() {
                    tips.removeClass( "ui-state-highlight", 1500 );
                }, 800 );
            }
      
             function checkLength(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips("La longueur de " + n + " doit être entre " +
                        min + " et " + max + ".");
                return false;
            } else {
                //     console.log("XXXXXX : " + o.val().replace(/"/g,"\\\""));
                o.val(o.val().replace(/\\/g, "\\\\"))
                o.val(o.val().replace(/"/g, "\\\""));
                return true;
            }
        }          
      
      function checkExist(o, n, min, max) {
            o.val($.trim(o.val()));
            if (o.val().length > max || o.val().length < min) {
                o.addClass("ui-state-error");
                updateTips(n + " est un champ obligtatoire.");
                return false;
            } else {
                return true;
            }
        }     
      
            function checkRegexp( o, regexp, n ) {
                if ( !( regexp.test( o.val() ) ) ) {
                    o.addClass( "ui-state-error" );
                    updateTips( n );
                    return false;
                } else {
                    return true;
                }
            }
            
            allFields.removeClass( "ui-state-error" );  
            
            bValid = bValid && checkExist( combopers, "Durée du compte", 1, 1 );
          
          console.log(combopers.val() + ' ' + bValid);
          
            if ( bValid === true) {
                
            tips.text('');       
            
            var mydata = "formid=" + encodeURIComponent(formid) + 
                         "&nbpers=" + encodeURIComponent(n) +
                         "&combopers=" + encodeURIComponent(combopers.val());
                        
            $.ajax({
                url:"/Arrubaguest/Create_Accounts",
                type : 'POST',
                traditionnal : 'true',
                data : mydata,
                beforeSend: function(xmlHttpRequest) { 
	 	     p.start();
                },
                complete: function() {
                      p.stop();
                }, 
                success : function(data){
                    console.log(data + " MOI");
                    if ( data === 'erreur database') {
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error").modal('show');
                   } else if ( data == 'ismax') {
                    $("#usemax").modal({backdrop:false});
                    $("#usemax").modal('show');
                   } else if (data === 'erreur PDF'){
                       $("#modal_error").modal({backdrop:false});
                       $("#modal_error .modal-body").append("<p><b>Erreur céation PDF</b>&nbsp;<span class=\"glyphicon glyphicon-warning-sign\"></span></p>");
                       $("#modal_error").modal('show');
                   } else {
                    $("#accok").modal('show');
                    pdfile = data;
                    }
                },
                error:function (xhr, ajaxOptions, thrownError){
                    p.stop();
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    $(location).attr('href',"/Arrubaguest/index.jsp");
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
            }
            });
        }  
            $("#arrubaform3")[0].reset();
         
         });
         
}

function gogo() {
    
    $('.typeahead').typeahead({
        minLength: 2,
        
        name : 'zob',
        source : {
            url: '/Arrubaguest/LireEvenement',
            filter: function (data) {
            console.log(data.data);
            }   
        }   
});
}

function Affiche_UserDenom(tud) {
    
    var nb = 0;
     
    $.map(tud, function(obj, value) {
        nb ++;
    });
    
    if (nb == 0) {
     //    $("#ens-contain").hide();
     //    $( "#tabens tbody" ).empty();
     //   $("<span class=\"infousers\"><b>Aucune dénomination trouvée</b></span>").appendTo("#Result_ens_denoms");
    } else {
        $("#tabev").show();
        $("#tabens tbody").empty();       
    
     //   $("#nom_ens").append("<b>" + tud[0].d_nom + " " + tud[0].d_prenom + "</b>  ");
     //   $("#ens_ref").append("<b>    Référentiel  " + cycle + "</b>  " );
         
        $.map(tud, function(obj, value) {
            $("#tabens tbody").append("<tr>" +
                    "<td>" + obj.evenement + "</td>" +
                    "<td>" + obj.createur + "</td>" +
                    "<td>" + obj.nbaccount + "</td>" +
                    "<td>" + obj.firstaccount + "</td>" +
                    "<td>" + obj.firstlogin + "</td>" +
                    "<td>" + obj.expiration + "</td>" +
                    "<td>" + obj.infos + "</td>" +
                    "<td>" + obj.id + "</td>" +
                    "</tr>")

        });
    }
}

function lire_radadmin () {

    var myobj = 'term=' + encodeURIComponent('') + "&action=" + 1;      
     
            console.log("IN AJAX")
            tab_radadmin.length = 0;
            
            $.ajax({
            url: '/Arrubaguest/LireEvenement',
            type: 'post',
            
            traditionnal : 'true',
            data: myobj,
            dataType: 'json',
            success: function (data) {        
                    $.each(data, function(i, user) {
                                          var ob = new Array(
                                            user.evenement,
                                            user.createur,
                                            user.nbacct,
                                            user.firstaccount,
                                            user.firstlogin,
                                            user.expiration,
                                            user.infos
                                        );
                              
                              if ( ob[4].indexOf('CEST') != -1) {
                                 ob[4] = ob[4].replace("CEST",'');
                              }
                                 ob[5] = ob[5].replace("CEST",'');
                                        tab_radadmin.push(ob);                  
                            console.log(i + " " + user.evenement + " " + ob[4] );
                    });
               //     Affiche_UserDenom(tab_radadmin);
               $("#tabens").dataTable().fnAddData(tab_radadmin);
               $("#tabev").show();
                },
            error:function (xhr, ajaxOptions, thrownError){
                 
                if (xhr.responseText.indexOf("expirée") >= 0) {
                    alert(xhr.responseText);
                    
                } else {
                    alert(xhr.status);
                    alert(thrownError);
                }
            }
            });
}

function lauch() {

$('#searchevnt').typeahead({
      
        minLength : 2,
        
        source: function (query, process) {
             
        user = [];
        umap = {};
        
     var myobj = 'term=' + encodeURIComponent(query) + "&action=" + 0;      
     
            console.log("IN AJAX")

            $.ajax({
            url: '/Arrubaguest/LireEvenement',
            type: 'post',
            
            data: myobj,
            dataType: 'json',
            success: function (data) {
                 
                 user = [];
                 umap = {};
                 
                 $.each(data, function(i, object) {
                        $.each(object, function(name, value) {
                            if (name === 'nom') {
                                umap[value + " (Début " + object.firstaccount.substr(1) + ")"] = object                             
                                    user.push(value + " (Début " + object.firstaccount.substr(1) + ")")                               
                            }
                  //          console.log(object + " " + object.firstacount);
                        })
                    })    
            
    
     /*
             $.map(data, function (i,item) {
                   console.log(item.nom);
                   umap[item.nom] = item.nom;
                   user.push(item.nom);
               });
     
              $.each(data, function (i, item) {
              umap[item.nom] = item
              user.push(item.nom)
              })
      */          
              return process(user);
    
            }
        });
},
/*
matcher: function (item) {
    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) !== -1) { 
   return true;
   }    
},
sorter: function (items) {
    return items.sort();
},
highlighter: function (item) {
    var regex = new RegExp( '(' + this.query + ')', 'gi' );
   // return item.replace( regex, "<i>$1</i>" );
    return item.replace( regex, "<strong>$1</strong>" );
},
*/

updater: function (item) {
    selecteditem = umap[item];
 //   console.log("Selection --> " + item + " " + selecteditem.nom + " " + selecteditem.firstaccount + " " + selecteditem.ID);
 
    o.nom = selecteditem.nom;
    o.id =  selecteditem.ID;
    o.firstacct = selecteditem.firstaccount;
    o.nombre = selecteditem.nombre;
 
    return item;
}
});
}
