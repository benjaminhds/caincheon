﻿window.daum=window.daum||{},function(){function t(t){var a={};return t.replace(/[?&]+([^=&]+)=([^&]*)/gi,function(t,e,r){a[e]=r}),a}function a(t){t&&document.write('<script charset="UTF-8" src="'+t+'"></script>')}function e(){if(d.length){var t=r(R[d.shift()],e);t.start()}else n()}function r(t,a){var e=document.createElement("script");return e.charset="utf-8",e.onload=a,e.onreadystatechange=function(){/loaded|complete/.test(this.readyState)&&a()},{start:function(){e.src=t||"",document.getElementsByTagName("head")[0].appendChild(e),
e=null}}}function n(){for(;I[0];)I.shift()();s.readyState=2}var s=daum.maps=daum.maps||{};if(void 0===s.readyState)s.onloadcallbacks=[],s.readyState=0;else if(2===s.readyState)return;s.VERSION={ROADMAP:"31sam",ROADMAP_SUFFIX:"",HYBRID:"31sam",SR:"2.00",ROADVIEW:"4.00",ROADVIEW_FLASH:"170321",BICYCLE:"3.00",USE_DISTRICT:"31sam",SKYVIEW_VERSION:"160114",SKYVIEW_HD_VERSION:"160107"
},s.RESOURCE_PATH={ROADVIEW_AJAX:"//s1.daumcdn.net/svc/attach/U03/cssjs/mapapi/ajax/170322/1490160180663/roadview.js"};for(var c,E="https:"==location.protocol?"https:":"http:",i="",o=document.getElementsByTagName("script"),S=o.length;c=o[--S];)if(/\/apis\.daum\.net\/maps\/maps3\.js\b/.test(c.src)){i=c.src;break}o=null;var I=s.onloadcallbacks,d=["v3"],l="",R={v3:E+"//s1.daumcdn.net/svc/attach/U03/cssjs/mapapi/3.5.16/1486533804955/open.js",services:E+"//s1.daumcdn.net/svc/attach/U03/cssjs/mapapi/libs/1441176450396/services.js",drawing:E+"//s1.daumcdn.net/svc/attach/U03/cssjs/mapapi/libs/1.2.3/1484905982985/drawing.js",clusterer:E+"//s1.daumcdn.net/svc/attach/U03/cssjs/mapapi/libs/1.0.6/1460434272434/clusterer.js"},_=t(i);l=_.apikey,l&&(s.apikey=l);var u=_.libraries;if(u&&(d=d.concat(u.split(","))),"false"!==_.autoload){
for(var S=0,f=d.length;S<f;S++)a(R[d[S]]);s.readyState=2}s.load=function(t){switch(I.push(t),s.readyState){case 0:s.readyState=1,e();break;case 2:n()}}}();