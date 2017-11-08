//
// Select the nearest/best proxy server for a URL.  The plan is to
// try and pick the closest proxy server depending on the ip address
// except for special cases.
//

//
// Function returns true if the url is one we cache
//
function basil_says_cache(host, url)
{
  if (url.substring(0, 5) == "http:" ||
      url.substring(0, 6) == "https:"||
      url.substring(0, 4) == "ftp:"||
      url.substring(0, 7) == "gopher:")
    return true;

  return false;
}
//
// The function the browser will call
//


function FindProxyForURL(url, host) {
 
    debugPAC ="PAC Debug Information\n";
    debugPAC +="-----------------------------------\n";
    debugPAC +="Machine IP: " + myIpAddress() + "\n";
    debugPAC +="Hostname: " + host + "\n";
    if (isResolvable(host)) {resolvableHost = "True"} else {resolvableHost = "False"};
    debugPAC +="Host Resolvable: " + resolvableHost + "\n";
    debugPAC +="Hostname IP: " + dnsResolve(host) + "\n";
    if (isPlainHostName(host)) {plainHost = "True"} else {plainHost = "False"};
    debugPAC +="Plain Hostname: " + plainHost + "\n";
    debugPAC +="Domain Levels: " + dnsDomainLevels(host) + "\n";
    debugPAC +="URL: " + url + "\n";
 
    // Protocol can only be determined by reading the entire URL.
    if (url.substring(0,5)=="http:") {protocol="HTTP";} else
        if (url.substring(0,6)=="https:") {protocol="HTTPS";} else
            if (url.substring(0,4)=="ftp:") {protocol="FTP";}
                else {protocol="Unknown";}
    debugPAC +="Protocol: " + protocol + "\n";
 
    // Reduce volume of alerts to a useable level, e.g. only alert on static text pages.
    alert(debugPAC);
 
    return "DIRECT";
}
