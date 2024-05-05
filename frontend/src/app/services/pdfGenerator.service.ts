import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PdfGeneratorService {

  constructor() { }

  generatePdf(userId: number | undefined, betData: any[]): void {
    const pdfContent = this.buildPdfContent(userId, betData);
    const blob = new Blob([pdfContent], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = 'bet_history.pdf';
    a.click();

    window.URL.revokeObjectURL(url);
  }

  private buildPdfContent(userId: number | undefined, betData: any[]): string {
    let pdfContent = `Historial de apuestas - Usuario: ${userId}\n\n`;

    if (betData.length === 0) {
      pdfContent += 'Aún no hay apuestas realizadas...\n';
    } else {
      betData.forEach((bet, index) => {
        pdfContent += `${index + 1}. ${bet.eventName}\nCantidad apostada: ${bet.betAmount}€\nCantidad ganada: ${bet.winningAmount}€\nBeneficio obtenido: ${bet.profit}€\n\n`;
      });
    }

    return pdfContent;
  }
}
