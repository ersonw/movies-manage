(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ff18b2d0"],{"133c":function(t,e,a){"use strict";a("7c25")},"1c18":function(t,e,a){},2423:function(t,e,a){"use strict";a.d(e,"a",(function(){return i}));var n=a("b775");function i(t){return Object(n["a"])({url:"/vue-element-admin/article/create",method:"post",data:t})}},2612:function(t,e,a){"use strict";a.d(e,"d",(function(){return i})),a.d(e,"b",(function(){return s})),a.d(e,"e",(function(){return l})),a.d(e,"c",(function(){return r})),a.d(e,"f",(function(){return o})),a.d(e,"a",(function(){return u}));a("e9c4");var n=a("b775");function i(t){return Object(n["a"])({url:"/users/getSmsRecordsList",method:"get",params:{data:t}})}function s(t){return Object(n["a"])({url:"/users/list",method:"get",params:{data:t}})}function l(t){return Object(n["a"])({url:"/users/superior",method:"get",params:{data:t}})}function r(t){return Object(n["a"])({url:"/users/share",method:"get",params:{data:t}})}function o(t){return t={data:JSON.stringify(t)},Object(n["a"])({url:"/users/update",method:"post",data:t})}function u(t){return t={data:JSON.stringify(t)},Object(n["a"])({url:"/users/delete",method:"post",data:t})}},"333d":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"pagination-container",class:{hidden:t.hidden}},[a("el-pagination",t._b({attrs:{background:t.background,"current-page":t.currentPage,"page-size":t.pageSize,layout:t.layout,"page-sizes":t.pageSizes,total:t.total},on:{"update:currentPage":function(e){t.currentPage=e},"update:current-page":function(e){t.currentPage=e},"update:pageSize":function(e){t.pageSize=e},"update:page-size":function(e){t.pageSize=e},"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}},"el-pagination",t.$attrs,!1))],1)},i=[];a("a9e3");Math.easeInOutQuad=function(t,e,a,n){return t/=n/2,t<1?a/2*t*t+e:(t--,-a/2*(t*(t-2)-1)+e)};var s=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function l(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function r(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function o(t,e,a){var n=r(),i=t-n,o=20,u=0;e="undefined"===typeof e?500:e;var c=function t(){u+=o;var r=Math.easeInOutQuad(u,n,i,e);l(r),u<e?s(t):a&&"function"===typeof a&&a()};c()}var u={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(t){this.$emit("update:page",t)}},pageSize:{get:function(){return this.limit},set:function(t){this.$emit("update:limit",t)}}},methods:{handleSizeChange:function(t){this.$emit("pagination",{page:this.currentPage,limit:t}),this.autoScroll&&o(0,800)},handleCurrentChange:function(t){this.$emit("pagination",{page:t,limit:this.pageSize}),this.autoScroll&&o(0,800)}}},c=u,d=(a("e498"),a("2877")),p=Object(d["a"])(c,n,i,!1,null,"6af373ef",null);e["a"]=p.exports},"3cbc":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"pan-item",style:{zIndex:t.zIndex,height:t.height,width:t.width}},[a("div",{staticClass:"pan-info"},[a("div",{staticClass:"pan-info-roles-container"},[t._t("default")],2)]),a("div",{staticClass:"pan-thumb",style:{backgroundImage:"url("+t.image+")"}})])},i=[],s=(a("a9e3"),{name:"PanThumb",props:{image:{type:String,required:!0},zIndex:{type:Number,default:1},width:{type:String,default:"150px"},height:{type:String,default:"150px"}}}),l=s,r=(a("133c"),a("2877")),o=Object(r["a"])(l,n,i,!1,null,"799537af",null);e["a"]=o.exports},"4e82":function(t,e,a){"use strict";var n=a("23e7"),i=a("1c0b"),s=a("7b0b"),l=a("d039"),r=a("a640"),o=[],u=o.sort,c=l((function(){o.sort(void 0)})),d=l((function(){o.sort(null)})),p=r("sort"),f=c||!d||!p;n({target:"Array",proto:!0,forced:f},{sort:function(t){return void 0===t?u.call(s(this)):u.call(s(this),i(t))}})},6724:function(t,e,a){"use strict";a("8d41");var n="@@wavesContext";function i(t,e){function a(a){var n=Object.assign({},e.value),i=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},n),s=i.ele;if(s){s.style.position="relative",s.style.overflow="hidden";var l=s.getBoundingClientRect(),r=s.querySelector(".waves-ripple");switch(r?r.className="waves-ripple":(r=document.createElement("span"),r.className="waves-ripple",r.style.height=r.style.width=Math.max(l.width,l.height)+"px",s.appendChild(r)),i.type){case"center":r.style.top=l.height/2-r.offsetHeight/2+"px",r.style.left=l.width/2-r.offsetWidth/2+"px";break;default:r.style.top=(a.pageY-l.top-r.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",r.style.left=(a.pageX-l.left-r.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return r.style.backgroundColor=i.color,r.className="waves-ripple z-active",!1}}return t[n]?t[n].removeHandle=a:t[n]={removeHandle:a},a}var s={bind:function(t,e){t.addEventListener("click",i(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[n].removeHandle,!1),t.addEventListener("click",i(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[n].removeHandle,!1),t[n]=null,delete t[n]}},l=function(t){t.directive("waves",s)};window.Vue&&(window.waves=s,Vue.use(l)),s.install=l;e["a"]=s},"7bae":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("el-input",{staticClass:"filter-item",staticStyle:{width:"20vw","margin-right":"1vw"},attrs:{placeholder:"用户昵称或者手机号或UID"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleFilter(e)}},model:{value:t.listQuery.title,callback:function(e){t.$set(t.listQuery,"title",e)},expression:"listQuery.title"}}),a("el-select",{staticClass:"filter-item",staticStyle:{width:"10vw","margin-right":"1vw"},attrs:{placeholder:"用户状态",clearable:""},model:{value:t.listQuery.status,callback:function(e){t.$set(t.listQuery,"status",e)},expression:"listQuery.status"}},t._l(t.statusMap,(function(t,e){return a("el-option",{key:e,attrs:{label:t,value:e}})})),1),a("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v(" 搜索 ")]),a("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-refresh"},on:{click:t.getList}},[t._v(" 刷新 ")])],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],key:t.tableKey,staticStyle:{width:"100%"},attrs:{data:t.list,border:"",fit:"","highlight-current-row":""},on:{"sort-change":t.sortChange}},[a("el-table-column",{attrs:{label:"ID",prop:"id",sortable:"custom",align:"center",width:"80","class-name":t.getSortClass("id")},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.id))])]}}])}),a("el-table-column",{attrs:{label:"头像",align:"center",width:"120px"},scopedSlots:t._u([{key:"default",fn:function(t){var e=t.row;return[a("pan-thumb",{attrs:{image:e.avatar||"/images/KV34RAJ9pn.png",height:"100px",width:"100px",hoverable:!1}})]}}])}),a("el-table-column",{attrs:{label:"背景",align:"center",width:"120px"},scopedSlots:t._u([{key:"default",fn:function(t){var e=t.row;return[a("el-image",{attrs:{src:e.bk_image||"/images/defaultwal_YkXkQnM0.jpg"}})]}}])}),a("el-table-column",{attrs:{label:"昵称","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[t._v(t._s(n.nickname))])]}}])}),a("el-table-column",{attrs:{label:"备注","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.reason))])]}}])}),a("el-table-column",{attrs:{label:"签名","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.signature))])]}}])}),a("el-table-column",{attrs:{label:"性别","min-width":"50px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[1===n.sex?a("span",[t._v("女")]):t._e(),0===n.sex?a("span",[t._v("男")]):t._e()]}}])}),a("el-table-column",{attrs:{label:"生日",width:"100px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(t._f("parseTime")(n.birthday,"{y}-{m}-{d}")))])]}}])}),a("el-table-column",{attrs:{label:"建立时间",width:"100px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(t._f("parseTime")(n.ctime,"{y}-{m}-{d} {h}:{i}")))])]}}])}),a("el-table-column",{attrs:{label:"更新时间",width:"100px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(t._f("parseTime")(n.utime,"{y}-{m}-{d} {h}:{i}")))])]}}])}),a("el-table-column",{attrs:{label:"金币","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.gold)+"币")])]}}])}),a("el-table-column",{attrs:{label:"钻石","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.diamond)+"钻石")])]}}])}),a("el-table-column",{attrs:{label:"余额","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[t._v("¥"+t._s(n.balance))])]}}])}),a("el-table-column",{attrs:{label:"状态","class-name":"status-col",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",{attrs:{type:t._f("statusTypeFilter")(n.status)}},[t._v(" "+t._s(t._f("statusFilter")(n.status))+" ")])]}}])}),a("el-table-column",{attrs:{label:"手机号","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.phone))])]}}])}),a("el-table-column",{attrs:{label:"邀请码","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.invite))])]}}])}),a("el-table-column",{attrs:{label:"被关注","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.follws))])]}}])}),a("el-table-column",{attrs:{label:"设备ID","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.identifier))])]}}])}),a("el-table-column",{attrs:{label:"找回UID","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.uid))])]}}])}),a("el-table-column",{attrs:{label:"上级","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[a("span",{staticClass:"link-type",on:{click:function(e){return t.handleSuperior(n.id)}}},[t._v(t._s(n.superior))])])]}}])}),a("el-table-column",{attrs:{label:"下属","min-width":"50px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[a("span",{staticClass:"link-type",on:{click:function(e){return t.handleFetchPv(n.id)}}},[t._v(t._s(n.share))])])]}}])}),a("el-table-column",{attrs:{label:"VIP过期时间","min-width":"100px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(t._f("parseTime")(n.expireds,"{y}-{m}-{d} {h}:{i}")))])]}}])}),a("el-table-column",{attrs:{label:"操作",align:"center",width:"150","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row,i=e.$index;return[a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){return t.handleUpdate(n)}}},[t._v(" 编辑 ")]),"deleted"!=n.status?a("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(n,i)}}},[t._v(" 删除 ")]):t._e()]}}])})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.page,limit:t.listQuery.limit},on:{"update:page":function(e){return t.$set(t.listQuery,"page",e)},"update:limit":function(e){return t.$set(t.listQuery,"limit",e)},pagination:t.getList}}),a("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("el-form",{ref:"dataForm",staticStyle:{width:"400px","margin-left":"50px"},attrs:{rules:t.rules,model:t.temp,"label-position":"left","label-width":"70px"}},[a("el-form-item",{attrs:{label:"状态"}},[a("el-select",{staticClass:"filter-item",attrs:{placeholder:"Please select"},model:{value:t.temp.status,callback:function(e){t.$set(t.temp,"status",e)},expression:"temp.status"}},t._l(t.statusMap,(function(t,e){return a("el-option",{key:e,attrs:{label:t,value:e}})})),1)],1),a("el-form-item",{attrs:{label:"生日"}},[a("el-date-picker",{attrs:{placeholder:"选择生日"},model:{value:t.temp.birthday,callback:function(e){t.$set(t.temp,"birthday",e)},expression:"temp.birthday"}})],1),a("el-form-item",{attrs:{label:"昵称"}},[a("el-input",{model:{value:t.temp.nickname,callback:function(e){t.$set(t.temp,"nickname",e)},expression:"temp.nickname"}})],1),a("el-form-item",{attrs:{label:"个性签名"}},[a("el-input",{attrs:{autosize:{minRows:2,maxRows:4},type:"textarea",placeholder:"Please input"},model:{value:t.temp.signature,callback:function(e){t.$set(t.temp,"signature",e)},expression:"temp.signature"}})],1),a("el-form-item",{attrs:{label:"备注"}},[a("el-input",{attrs:{autosize:{minRows:2,maxRows:4},type:"textarea",placeholder:"Please input"},model:{value:t.temp.reason,callback:function(e){t.$set(t.temp,"reason",e)},expression:"temp.reason"}})],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v(" 取消 ")]),a("el-button",{attrs:{type:"primary"},on:{click:function(e){"create"===t.dialogStatus?t.createData():t.updateData()}}},[t._v(" 确认 ")])],1)],1),a("el-dialog",{attrs:{visible:t.dialogPvVisible,title:"上级信息"},on:{"update:visible":function(e){t.dialogPvVisible=e}}},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.pvData,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"头像",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(t){var e=t.row;return[a("pan-thumb",{attrs:{image:e.avatar||"/images/KV34RAJ9pn.png",height:"100px",width:"100px",hoverable:!1}})]}}])}),a("el-table-column",{attrs:{label:"昵称"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[a("span",{staticClass:"link-type",on:{click:function(e){return t.handleSearch(n.nickname)}}},[t._v(t._s(n.nickname))])])]}}])}),a("el-table-column",{attrs:{label:"手机号"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[a("span",{staticClass:"link-type",on:{click:function(e){return t.handleSearch(n.phone)}}},[t._v(t._s(n.phone))])])]}}])}),a("el-table-column",{attrs:{label:"下属","min-width":"150px"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("el-tag",[a("span",{staticClass:"link-type",on:{click:function(e){return t.handleFetchPv(n.id)}}},[t._v(t._s(n.share))])])]}}])})],1),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.dialogPvVisible=!1}}},[t._v("关闭")])],1)],1)],1)},i=[],s=(a("4e82"),a("c740"),a("a434"),a("d81d"),a("2423")),l=a("6724"),r=a("ed08"),o=a("333d"),u=a("2612"),c=a("3cbc"),d={components:{Pagination:o["a"],PanThumb:c["a"]},directives:{waves:l["a"]},filters:{statusTypeFilter:function(t){var e=["danger","success"];return e[t]},statusFilter:function(t){var e=["禁用","启用"];return e[t]},typeFilter:function(t){}},data:function(){return{statusMap:["禁用","启用"],tableKey:0,list:null,total:0,listLoading:!0,listQuery:{page:1,limit:20,title:void 0,sort:"+id"},temp:{status:1},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"Edit",create:"Create"},dialogPvVisible:!1,pvData:[],rules:{type:[{required:!0,message:"type is required",trigger:"change"}],timestamp:[{type:"date",required:!0,message:"timestamp is required",trigger:"change"}],title:[{required:!0,message:"title is required",trigger:"blur"}]},downloadLoading:!1}},created:function(){this.getList()},methods:{handleSearch:function(t){this.listQuery.page=1,this.listQuery.title=t,this.getList()},getList:function(){var t=this;this.listLoading=!0,Object(u["b"])(this.listQuery).then((function(e){t.list=e.data.list,t.total=e.data.total,setTimeout((function(){t.listLoading=!1}),1500)}))},handleFilter:function(){this.listQuery.page=1,this.getList()},handleModifyStatus:function(t,e){this.$message({message:"操作Success",type:"success"}),t.status=e},sortChange:function(t){var e=t.prop,a=t.order;"id"===e&&this.sortByID(a)},sortByID:function(t){this.listQuery.sort="ascending"===t?"+id":"-id",this.handleFilter()},resetTemp:function(){this.temp={status:1}},handleCreate:function(){var t=this;this.resetTemp(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},createData:function(){var t=this;this.$refs["dataForm"].validate((function(e){e&&(t.temp.id=parseInt(100*Math.random())+1024,t.temp.author="vue-element-admin",Object(s["a"])(t.temp).then((function(){t.list.unshift(t.temp),t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Created Successfully",type:"success",duration:2e3})})))}))},handleUpdate:function(t){var e=this;this.temp=Object.assign({},t),this.temp.timestamp=new Date(this.temp.timestamp),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},updateData:function(){var t=this;this.$refs["dataForm"].validate((function(e){if(e){var a=Object.assign({},t.temp);a.birthday=new Date(a.birthday).getTime();var n={id:a.id,status:a.status,birthday:a.birthday,nickname:a.nickname,signature:a.signature,reason:a.reason};Object(u["f"])(n).then((function(){var e=t.list.findIndex((function(e){return e.id===t.temp.id}));t.list.splice(e,1,t.temp),t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3})}))}}))},handleDelete:function(t,e){var a=this;this.$confirm("确定需要删除用户【"+t.nickname+"】吗？操作不可恢复!","删除提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(u["a"])({id:t.id}).then((function(){a.$notify({title:"Success",message:"Delete Successfully",type:"success",duration:2e3}),a.getList()}))}))},handleSuperior:function(t){var e=this;Object(u["e"])({id:t}).then((function(t){e.pvData=t.data.list,e.dialogPvVisible=!0}))},handleFetchPv:function(t){var e=this;Object(u["c"])({id:t}).then((function(t){e.pvData=t.data.list,e.dialogPvVisible=!0}))},formatJson:function(t){return this.list.map((function(e){return t.map((function(t){return"timestamp"===t?Object(r["d"])(e[t]):e[t]}))}))},getSortClass:function(t){var e=this.listQuery.sort;return e==="+".concat(t)?"ascending":"descending"}}},p=d,f=a("2877"),m=Object(f["a"])(p,n,i,!1,null,null,null);e["default"]=m.exports},"7c25":function(t,e,a){},"8d41":function(t,e,a){},e498:function(t,e,a){"use strict";a("1c18")}}]);