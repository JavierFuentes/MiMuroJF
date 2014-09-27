/**
 * Created by javierfuentes on 15/06/14.
 */

var companyData = {
    author: "Javier Fuentes",

    getCurrYear: function () {
        var myDate = new Date();
        return myDate.getFullYear();
    },

    getCopyright: function () {
        return '&copy; ' + this.getCurrYear() + ' ' + this.author
    }
};

var httpErrorManagement = {

    // Response es la respuesta fallida de un WS de Play, por lo que tiene su misma estructura original
    getFormatedMsg: function (Response) {
        var msg = '';

        switch (Response.status) {
            case 0:
                msg = 'El servicio no existe. ' + Response.data;
                break;

            case 400:  // badRequest
                msg = 'No se ha podido ejecutar la accion solicitada. ' + Response.data;
                break;

            case 401:  // unauthorized
                msg = 'No autorizado. ' + Response.data;
                break;

            case 404:  // notFound
                msg = 'El Servidor no esta activo o no se ha encontrado el servicio solicitado. ' + Response.data;
                break;

            default :  // p.e: internalServerError (500)
                msg = 'Error ' + Response.status.toString() + ' - ' + Response.statusText + '. ' + Response.data;
                break;
        }

        return msg;
    }
};

var myUtils = {

        getLocaleDateString: function (longValue) {
            var myDate = new Date(longValue);
            return myDate.toLocaleString();
        },

        dmyTOymd: function (originalDate) {
            if (originalDate)
                var myDate = Date.parseExact(originalDate, ['ddMMyyyy', 'dMMyyyy', 'ddMyyyy']);

            if (myDate) {
                var myMonth = myDate.getMonth() + 1;
                var myDay = myDate.getDate();

                if (myMonth < 10)
                    myMonth = '0' + myMonth.toString();

                if (myDay < 10)
                    myDay = '0' + myDay.toString();

                //myDate.toLocaleDateString(); // No termina de funcionar bien en el HTML ¿?
                // Este parece ser el formato compatible con el control HTML
                myDate = myDate.getFullYear().toString() + '-' + myMonth + '-' + myDay;
            }

            if (!myDate)
                myDate = "N/D";

            return myDate;
        },

        ymdTOdmy: function (originalDate) {
            if ((!originalDate) || (originalDate === "N/D"))
                return null;

            var myDate = Date.parseExact(originalDate, ['yyyy-MM-dd', 'yyyy-M-dd', 'yyyy-MM-d', 'yyyy-M-d']);

            if (!myDate)
                return null;

            var myMonth = myDate.getMonth() + 1;
            var myDay = myDate.getDate();

            if (myMonth < 10)
                myMonth = '0' + myMonth.toString();

            if (myDay < 10)
                myDay = '0' + myDay.toString();

            // (Mismo formato que la carga del .json)
            myDate = myDay.toString() + myMonth.toString() + myDate.getFullYear().toString();

            return myDate;
        },

        copyObject: function (orig) {

            function copyOwnPropertiesFrom(target, source) {
                Object.getOwnPropertyNames(source)
                    .forEach(function (propKey) {
                        var desc = Object.getOwnPropertyDescriptor(source, propKey);
                        Object.defineProperty(target, propKey, desc);
                    });

                return target;
            }

            // 1. copy has same prototype as orig
            var copy = Object.create(Object.getPrototypeOf(orig));
            // 2. copy has all of orig’s properties
            copyOwnPropertiesFrom(copy, orig);

            return copy;
        },

        sortById: function (a, b) {
            return a.id.localeCompare(b.id);
        },

// Esto nos sirve para los ng-Repeat (p.e. de un radio-button)
        /*
         getBoolList: function () {
         // Key-Label: Value
         return {
         No: false,
         Si: true
         }
         },

         // Esto nos sirve para los ng-Options de un select-box
         getSexList: function () {
         return [
         {
         sexLabel: "Hombre",
         sexValue: 1
         },
         {
         sexLabel: "Mujer",
         sexValue: 2
         }
         ]
         }
         */

//   *-------------------------------------------------*
//  || Estas versiones son para el escenario en el que ||
//  || se transforman los datos que llegan del origen  ||
//  || para facilitar el filtrado al usuario.          ||
//   *-------------------------------------------------*

        getBoolList: function () {
            // Key-Label: Value
            return {
                No: "No",
                Si: "Si"
            }
        },

// Esto nos sirve para los ng-Options de un select-box
        getSexList: function () {
            return [
                {
                    sexLabel: "Hombre",
                    sexValue: "Hombre"
                },
                {
                    sexLabel: "Mujer",
                    sexValue: "Mujer"
                }
            ]
        }
    }
    ;


