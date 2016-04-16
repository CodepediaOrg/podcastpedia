<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
  <c:when test="${numberOfPages >= 5}">
    <c:choose>
      <c:when test="${advancedSearchResult.currentPage == 1}">
        <ul>

          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=1" var="firstPageUrl"/>
            <a href="${firstPageUrl}" class="currentpage"> 1 </a>
          </li>
          <li>
            <a href="${lastPageURL}" class="currentpage prev-page"><spring:message code="search.Previous"/></a>
          </li>
          <c:forEach begin="2" end="4" var="i">
            <li>
              <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="firstSelectedURL"/>
              <a href="${firstSelectedURL}"> ${i} </a>
            </li>
          </c:forEach>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=2" var="nextPageURL"/>
            <a href="${nextPageURL}" class="next-page"><spring:message code="search.Next"/></a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages}" var="lastPageURL"/>
            <a href="${lastPageURL}">${numberOfPages} </a>
          </li>
        </ul>
      </c:when>
      <c:when test="${advancedSearchResult.currentPage == 2}">
        <ul>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=1" var="firstPageUrl"/>
            <a href="${firstPageUrl}"> 1 </a>
          </li>
          <li>
            <a href="${firstPageUrl}" class="prev-page"><spring:message code="search.Previous"/></a>
          </li>
          <c:forEach begin="1" end="5" var="i">
            <c:choose>
              <c:when test="${i == 2}">
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="firstSelectedURL"/>
                  <a href="${firstSelectedURL}"  class="currentpage"> ${i} </a>
                </li>
              </c:when>
              <c:otherwise>
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="selectedPageURL"/>
                  <a href="${selectedPageURL}"> ${i} </a>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=3" var="nextPageURL"/>
            <a href="${nextPageURL}" class="next-page"><spring:message code="search.Next"/></a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages}" var="lastPageURL"/>
            <a href="${lastPageURL}"> ${numberOfPages} </a>
          </li>
        </ul>
      </c:when>
      <c:when test="${advancedSearchResult.currentPage == numberOfPages - 1}">
        <ul>
          <!-- first results page -->
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=1" var="firstPageURL"/>
            <a href="${firstPageURL}">1 </a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages-2}" var="previousPageURL"/>
            <a href="${previousPageURL}" class="prev-page"><spring:message code="search.Previous"/></a>
          </li>
          <c:forEach begin="${numberOfPages - 4}" end="${numberOfPages}" var="i">
            <c:choose>
              <c:when test="${i == numberOfPages - 1}">
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="firstSelectedURL"/>
                  <a href="${firstSelectedURL}" class="currentpage"> ${i} </a>
                </li>
              </c:when>
              <c:otherwise>
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="selectedPageURL"/>
                  <a href="${selectedPageURL}"> ${i} </a>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages}" var="lastPageURL"/>
          <li>
            <a href="${lastPageURL}" class="next-page"><spring:message code="search.Next"/></a>
          </li>
          <li>
            <a href="${lastPageURL}"> ${numberOfPages} </a>
          </li>
        </ul>
      </c:when>
      <c:when test="${advancedSearchResult.currentPage == numberOfPages}">
        <ul>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=1" var="firstPageURL"/>
            <a href="${firstPageURL}"> 1 </a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages-1}" var="previousPageURL"/>
            <a href="${previousPageURL}" class="prev-page"><spring:message code="search.Previous"/></a>
          </li>
          <c:forEach begin="${numberOfPages - 4}" end="${numberOfPages-1}" var="i">
            <li>
              <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="selectedPageURL"/>
              <a href="${selectedPageURL}">${i} </a>
            </li>
          </c:forEach>
          <li>
            <a href="${lastPageURL}" class="currentpage next-page"><spring:message code="search.Next"/></a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages}" var="lastPageURL"/>
            <a href="${lastPageURL}"  class="currentpage">${numberOfPages} </a>
          </li>
        </ul>
      </c:when>
      <c:otherwise>
        <ul>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=1" var="firstPageURL"/>
            <a href="${firstPageURL}"> 1 </a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${advancedSearchResult.currentPage-1}" var="previousPageURL"/>
            <a href="${previousPageURL}" class="prev-page"><spring:message code="search.Previous"/></a>
          </li>
          <c:forEach begin="${advancedSearchResult.currentPage - 2}" end="${advancedSearchResult.currentPage + 2}" var="i">
            <c:choose>
              <c:when test="${i == advancedSearchResult.currentPage}">
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="firstSelectedURL"/>
                  <a href="${firstSelectedURL}" class="currentpage"> ${i} </a>
                </li>
              </c:when>
              <c:otherwise>
                <li>
                  <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="selectedPageURL"/>
                  <a href="${selectedPageURL}"> ${i} </a>
                </li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${advancedSearchResult.currentPage+1}" var="nextPageURL"/>
            <a href="${nextPageURL}" class="next-page"><spring:message code="search.Next"/></a>
          </li>
          <li>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages}" var="lastPageURL"/>
            <a href="${lastPageURL}"> ${numberOfPages} </a>
          </li>
        </ul>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
    <ul>
      <c:forEach begin="1" end="${numberOfPages}" var="i">
        <c:choose>
          <c:when test="${advancedSearchResult.currentPage == i}">
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="advancedSearchUrl"/>
            <a href="${advancedSearchUrl}" class="currentpage"> ${i}</a>
          </c:when>
          <c:otherwise>
            <c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${i}" var="advancedSearchUrl"/>
            <a href="${advancedSearchUrl}">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </ul>
  </c:otherwise>
</c:choose>
