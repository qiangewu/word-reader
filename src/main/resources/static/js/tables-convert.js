var page = require("webpage").create();
var system = require("system");
var args = system.args;
var pageURL = params.outfile;
page.open(pageURL,function(status){
    console.log(pageURL);
    if(status=='success'){
        page.render(params.outfile);
        console.log('success');

    }else{
        console.log('渲染失败');
    }
    phantom.exit();
})